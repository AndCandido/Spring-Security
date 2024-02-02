package io.github.AndCandido.apispringsecurity.controllers;

import com.auth0.jwt.exceptions.JWTCreationException;
import io.github.AndCandido.apispringsecurity.models.User;
import io.github.AndCandido.apispringsecurity.models.dtos.AuthLoginDto;
import io.github.AndCandido.apispringsecurity.services.ITokenService;
import io.github.AndCandido.apispringsecurity.services.IUserService;
import io.github.AndCandido.apispringsecurity.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final IUserService userService;
    private final ITokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, UserService userService, ITokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid AuthLoginDto authLoginDto) {
        Authentication authenticate;
        try {
            UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(authLoginDto.username(), authLoginDto.password());
            authenticate = authenticationManager.authenticate(authToken);
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username or password is incorrect");
        }

        String token;
        try {
            token = tokenService.generateToken((UserDetails) authenticate.getPrincipal());
        } catch (JWTCreationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failure");
        }

        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
}
