package io.github.AndCandido.apispringsecurity.exceptions;

public class CredentialsInvalidException extends RuntimeException {
    public CredentialsInvalidException(String message) {
        super(message);
    }
}
