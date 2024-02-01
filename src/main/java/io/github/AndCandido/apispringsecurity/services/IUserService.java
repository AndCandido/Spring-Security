package io.github.AndCandido.apispringsecurity.services;

import io.github.AndCandido.apispringsecurity.entities.User;

public interface IUserService {
    User saveUser(User user);
}
