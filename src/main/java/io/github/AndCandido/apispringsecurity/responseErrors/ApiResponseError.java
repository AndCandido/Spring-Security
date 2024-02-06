package io.github.AndCandido.apispringsecurity.responseErrors;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ApiResponseError(
    String message,
    int status,
    String error,
    LocalDateTime timestamp
) {
}
