package io.github.AndCandido.apispringsecurity.models.dtos;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginDto(

    @NotBlank
    String username,

    @NotBlank
    String password
) {
}
