package io.github.AndCandido.apispringsecurity.dtos.req;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginDto(

    @NotBlank
    String username,

    @NotBlank
    String password
) {
}
