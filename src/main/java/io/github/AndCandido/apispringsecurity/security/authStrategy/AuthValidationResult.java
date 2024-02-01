package io.github.AndCandido.apispringsecurity.security.authStrategy;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;

public record AuthValidationResult(
    boolean isValid,
    UserDetails user,
    String messageError,
    HttpStatus statusError
) {

    public static AuthValidationResult failure(String messageError, HttpStatus statusError) {
        return new AuthValidationResult(false, null, messageError, statusError);
    }

    public static AuthValidationResult success(UserDetails user) {
        return new AuthValidationResult(true, user,null, null);
    }
}
