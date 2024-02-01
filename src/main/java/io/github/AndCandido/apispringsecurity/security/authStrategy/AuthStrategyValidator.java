package io.github.AndCandido.apispringsecurity.security.authStrategy;

public interface AuthStrategyValidator {
    AuthValidationResult validate(String token);
}
