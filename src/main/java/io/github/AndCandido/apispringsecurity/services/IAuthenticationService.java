package io.github.AndCandido.apispringsecurity.services;

import io.github.AndCandido.apispringsecurity.dtos.req.AuthLoginDto;
import io.github.AndCandido.apispringsecurity.models.User;

public interface IAuthenticationService {
    User register(User user);
    String login(AuthLoginDto authLoginDto);
}
