package com.pghub.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {

    private Long id;

    @NotBlank(message = "User name cannot be blank")
    @Size(max = 30, message = "User name must be at most 30 characters")
    private String userName;

    @NotBlank(message = "Password cannot be blank")
    @Size(max = 120, message = "Password must be at most 120 characters")
    private String password;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    @Size(max = 50, message = "Email must be at most 50 characters")
    private String email;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String userPhoneNo;

    @NotNull(message = "PG ID cannot be null")
    private Integer pgId;

    private AdminDTO admin; // Many users belong to one admin.

    private String userImage;

    @NotNull(message = "Gender cannot be null")
    private Character gender;

    @NotNull(message = "Room number cannot be null")
    private Integer roomNo;

    private String role;

    private LocalDateTime createdAt;
}
