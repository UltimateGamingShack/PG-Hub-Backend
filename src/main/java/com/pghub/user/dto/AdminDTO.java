package com.pghub.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class AdminDTO {

    private Long adminId;

    @NotBlank(message = "Admin name cannot be blank")
    @Size(max = 30, message = "Admin name must be at most 30 characters")
    private String adminName;

    @NotBlank(message = "Admin email cannot be blank")
    @Email(message = "Invalid email format")
    @Size(max = 50, message = "Admin email must be at most 50 characters")
    private String adminEmail;

    @NotBlank(message = "Password cannot be blank")
    @Size(max = 120, message = "Password must be at most 120 characters")
    private String adminPassword;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String adminPhoneno;

    @NotNull(message = "PG ID cannot be null")
    private Integer pgId;

    private Set<UserDTO> users; // One admin can manage many users.

    private Set<CookDTO> cooks; // One admin can manage many cooks.

    private String role;
}
