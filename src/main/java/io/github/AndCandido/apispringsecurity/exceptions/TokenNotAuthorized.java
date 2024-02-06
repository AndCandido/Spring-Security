package io.github.AndCandido.apispringsecurity.exceptions;

public class TokenNotAuthorized extends RuntimeException {
    public TokenNotAuthorized(String message) {
        super(message);
    }
}
