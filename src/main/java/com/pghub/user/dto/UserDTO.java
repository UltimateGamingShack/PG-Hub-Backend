package com.pghub.user.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be valid with 10-15 digits")
    private String phoneNo;

    @NotNull(message = "PG ID is required")
    @Min(value = 1, message = "PG ID must be a positive number")
    private Integer pgId;

    @Lob
    private String userImage;

    @NotNull(message = "Gender is required")
    @Pattern(regexp = "^[MFmf]$", message = "Gender must be 'M' or 'F'")
    private Character gender;

    @Min(value = 1, message = "Room number must be a positive integer")
    private Integer roomNo;

    private Set<Long> roleIds; // IDs of roles assigned to the user
}
