package ru.korshun.solbeg.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFilter
        extends OncePerRequestFilter {

    private final CustomTokenProvider tokenProvider;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {

        try {
            String token = tokenProvider.getTokenFromRequest(request);
            if (token != null && tokenProvider.checkToken(token)) {
                Authentication auth = tokenProvider.getAuthentication(token).orElseThrow(() ->
                        new IllegalArgumentException("Authentication error"));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (AuthenticationException e) {
            logger.error("Authentication error", e);
            authenticationEntryPoint.commence(request, response, e);
            return;
        }

        filterChain.doFilter(request, response);
    }

}