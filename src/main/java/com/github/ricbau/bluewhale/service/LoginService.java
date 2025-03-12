package com.github.ricbau.bluewhale.service;

import com.github.ricbau.bluewhale.config.jwt.JwtRepo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {
    private final JwtRepo jwtRepo;

    public LoginToken login(LoginCredentials loginCredentials) {
        return new LoginToken(jwtRepo.generateToken(loginCredentials.username()));
    }

    public record LoginToken(String token) {
    }

    public record LoginCredentials(@NotNull @NotBlank String username) {
    }
}
