package com.pghub.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CookDTO {

    private Long cookId;

    @NotNull(message = "PG ID cannot be null")
    private Integer pgId;

    @NotBlank(message = "Cook name cannot be blank")
    @Size(max = 30, message = "Cook name must be at most 30 characters")
    private String name;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String cookPhoneno;

    @NotBlank(message = "Password cannot be blank")
    @Size(max = 120, message = "Password must be at most 120 characters")
    private String password;

    private AdminDTO admin; // Many cooks belong to one admin.

    private String role;

    private LocalDateTime createdAt;
}
