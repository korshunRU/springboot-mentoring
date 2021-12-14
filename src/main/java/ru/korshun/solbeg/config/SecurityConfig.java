package ru.korshun.solbeg.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import ru.korshun.solbeg.security.CustomAccessDeniedHandler;
import ru.korshun.solbeg.security.CustomUserDetailsService;
import ru.korshun.solbeg.security.CustomAuthenticationEntryPoint;
import ru.korshun.solbeg.security.CustomAuthenticationFilter;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final CustomUserDetailsService userDetailsService;
  private final CustomAuthenticationEntryPoint authenticationEntryPoint;
  private final CustomAuthenticationFilter authenticationFilter;
  private final CustomAccessDeniedHandler accessDeniedHandler;

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(getPasswordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
            .cors().and()
            .csrf().disable()
            .exceptionHandling().accessDeniedHandler(accessDeniedHandler).and()
            .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests()
            .antMatchers("/auth/**").permitAll()
            .antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
            .antMatchers("/api/books/**").permitAll()
            .anyRequest().authenticated().and()
            .httpBasic().and()
            .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

  }

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source =
            new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("http://localhost:8888");
    config.addAllowedHeader("*");
    config.setAllowedMethods(Arrays.asList("GET", "OPTIONS", "POST", "PUT"));
    config.applyPermitDefaultValues();
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean(BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

}
