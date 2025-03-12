package com.github.ricbau.bluewhale.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtRepo {
    private final JwtProperties jwtProperties;
    private final SecretKey secretKey = Jwts.SIG.HS256.key().build();

    public String generateToken(String username) {

        return Jwts.builder()
                .subject((username))
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtProperties.getExpirationInMillis()))
                .signWith(secretKey)
                .compact();
    }

    public Jws<Claims> validate(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);
    }
}
