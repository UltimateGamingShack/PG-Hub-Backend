package com.pghub.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailVerificationRequestDto(
        @NotBlank(message = "Email is required")
        @Email
        String email,
        @NotBlank(message = "PgName is required")
        String pgName,
        @NotBlank(message = "roomNo is required")
        String roomNo,

        @NotBlank(message = "Otp is required")
        String otp
) {
}