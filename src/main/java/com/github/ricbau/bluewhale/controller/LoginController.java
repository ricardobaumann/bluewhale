package com.github.ricbau.bluewhale.controller;

import com.github.ricbau.bluewhale.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.github.ricbau.bluewhale.service.LoginService.LoginCredentials;
import static com.github.ricbau.bluewhale.service.LoginService.LoginToken;

@RestController
@AllArgsConstructor
@RequestMapping("/v1")
public class LoginController {
    private final LoginService loginService;

    @Operation(summary = "Execute login", description = "Get a token for a username")
    @PostMapping("/login")
    public LoginToken login(@RequestBody @NotNull @Valid LoginCredentials loginCredentials) {
        return loginService.login(loginCredentials);
    }
}
