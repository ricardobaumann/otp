package com.github.ricbau.otp.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginInput(
        @NotBlank @NotNull String username,
        @NotBlank @NotNull String otp
) {
}
