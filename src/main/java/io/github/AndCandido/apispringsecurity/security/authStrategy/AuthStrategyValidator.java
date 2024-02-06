package io.github.AndCandido.apispringsecurity.security.authStrategy;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthStrategyValidator {
    UserDetails validate(String token) throws RuntimeException;
}
