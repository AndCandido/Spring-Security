package io.github.AndCandido.apispringsecurity.controllers;

import io.github.AndCandido.apispringsecurity.entities.User;
import io.github.AndCandido.apispringsecurity.services.IUserService;
import io.github.AndCandido.apispringsecurity.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final IUserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
}
