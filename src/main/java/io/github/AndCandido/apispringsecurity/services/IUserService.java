package io.github.AndCandido.apispringsecurity.services;

import io.github.AndCandido.apispringsecurity.models.User;

public interface IUserService {
    User saveUser(User user);
}
