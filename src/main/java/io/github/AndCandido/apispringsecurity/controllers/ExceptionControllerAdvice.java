package io.github.AndCandido.apispringsecurity.controllers;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import io.github.AndCandido.apispringsecurity.exceptions.CredentialsInvalidException;
import io.github.AndCandido.apispringsecurity.exceptions.TokenNotAuthorized;
import io.github.AndCandido.apispringsecurity.responseErrors.ApiResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    private static final HttpStatus STATUS_UNAUTHORIZED = HttpStatus.UNAUTHORIZED;

    @ExceptionHandler({
        JWTCreationException.class,
        JWTVerificationException.class,
    })
    public ResponseEntity<ApiResponseError> handlerAuthenticationException(RuntimeException ex) {
        ApiResponseError apiResponseError = instanceApiResponseError(ex.getMessage());
        return ResponseEntity.status(STATUS_UNAUTHORIZED).body(apiResponseError);
    }

    private ApiResponseError instanceApiResponseError(String message) {
        return ApiResponseError.builder()
            .message(message)
            .status(STATUS_UNAUTHORIZED.value())
            .error(STATUS_UNAUTHORIZED.getReasonPhrase())
            .timestamp(LocalDateTime.now())
            .build();
    }

}
