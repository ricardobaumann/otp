package com.github.ricbau.otp.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record OtpInput(
        @NotBlank @Email String email
) {
}
