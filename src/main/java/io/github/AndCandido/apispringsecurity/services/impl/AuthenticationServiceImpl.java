package io.github.AndCandido.apispringsecurity.services.impl;

import io.github.AndCandido.apispringsecurity.dtos.req.AuthLoginDto;
import io.github.AndCandido.apispringsecurity.models.User;
import io.github.AndCandido.apispringsecurity.services.IAuthenticationService;
import io.github.AndCandido.apispringsecurity.services.ITokenService;
import io.github.AndCandido.apispringsecurity.services.IUserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final IUserService userService;
    private final ITokenService tokenService;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, UserServiceImpl userService, ITokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenService = tokenService;
    }


    @Override
    public User register(User user) {
        return userService.saveUser(user);
    }

    @Override
    public String login(AuthLoginDto authLoginDto) {
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(authLoginDto.username(), authLoginDto.password());
        Authentication authenticate = authenticationManager.authenticate(authToken);

        return tokenService.generateToken((UserDetails) authenticate.getPrincipal());
    }
}
