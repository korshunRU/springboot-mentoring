package ru.korshun.solbeg.security;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.korshun.solbeg.entity.PrivilegeEntity;
import ru.korshun.solbeg.entity.RoleEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
@RequiredArgsConstructor
public class CustomTokenProvider {

    @Value("${custom.jwt.tag}")
    private String tag;

    @Value("${custom.jwt.secret}")
    private String secret;

    @Value("${custom.jwt.expirationTime}")
    private long expirationTime;

    private final CustomUserDetailsService userDetailsService;


    public String generateToken(String email, Set<RoleEntity> roles) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", getRoleNames(roles));
        Date expiration = new Date(new Date().getTime() + expirationTime);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }


    public Optional<Authentication> getAuthentication(String token) throws AuthenticationException {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(getEmailFromToken(token));
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            "",
                            userDetails.getAuthorities()
                    );
            return Optional.of(authentication);
        } catch (Exception exception) {
            throw new BadCredentialsException("JWT token invalid");
        }
    }


    public String getEmailFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    public String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token == null || !token.startsWith(tag))
            return null;
        return token.substring(tag.length());
    }


    public boolean checkToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new BadCredentialsException("Token is expired or invalid!");
        }
    }

    public Set<String> getRoleNames(Set<RoleEntity> userRoles) {
        Set<String> rolesNames = new HashSet<>();

        if (userRoles == null)
            return rolesNames;

        userRoles.forEach((role) -> {
            Set<PrivilegeEntity> permissions = role.getPrivileges();
            permissions.forEach((authority) -> rolesNames.add(authority.getName()));
            rolesNames.add(role.getName());
        });
        return rolesNames;
    }


}
