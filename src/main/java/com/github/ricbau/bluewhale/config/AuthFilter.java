package com.github.ricbau.bluewhale.config;

import com.github.ricbau.bluewhale.config.jwt.JwtRepo;
import com.github.ricbau.bluewhale.config.jwt.JwtUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Collections;
import java.util.Optional;

@Component
@AllArgsConstructor
@SuppressWarnings("NullableProblems")
public class AuthFilter extends OncePerRequestFilter {
    private final JwtRepo jwtRepo;

    @SneakyThrows
    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) {
        Optional.ofNullable(request.getHeader("Authorization"))
                .filter(authorization -> authorization.startsWith("Bearer "))
                .map(bearer -> bearer.substring(7))
                .map(jwtRepo::parse)
                .map(Jwt::getPayload)
                .map(Claims::getSubject)
                .map(JwtUserDetails::new)
                .map(jwtUserDetails -> new UsernamePasswordAuthenticationToken(jwtUserDetails, null, Collections.emptyList()))
                .ifPresent(
                        authentication -> SecurityContextHolder.getContext().setAuthentication(authentication));
        filterChain.doFilter(request, response);
    }
}
