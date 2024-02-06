package io.github.AndCandido.apispringsecurity.controllers;

import io.github.AndCandido.apispringsecurity.dtos.res.LoginResponseDto;
import io.github.AndCandido.apispringsecurity.models.User;
import io.github.AndCandido.apispringsecurity.dtos.req.AuthLoginDto;
import io.github.AndCandido.apispringsecurity.services.IAuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final IAuthenticationService authenticationService;

    public AuthenticationController(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid AuthLoginDto authLoginDto) {
        String token = authenticationService.login(authLoginDto);

        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping("/register")
    public User saveUser(@RequestBody User user) {
        return authenticationService.register(user);
    }
}
