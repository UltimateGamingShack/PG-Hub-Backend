package com.pghub.user.payload.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.parameters.P;

import java.util.Set;
 @Getter
 @Setter
public class SignupRequest {
    @NotBlank(message = "{username_invalid_empty}")
    @Size(min = 3, max = 20, message = "{username_invalid_size}")
    private String username;
    @NotBlank(message = "{email_invalid_empty}")
    @Size(max = 50, message = "{email_invalid_size}")
    @Email(message = "{email_invalid_format}")
    private String email;

    @NotNull(message = "{gender_invalid_empty}")
    @Pattern(regexp = "([MFO])", message = "{gender_invalid_choice}")
     private Character gender;

     private Integer roomNo;
    @NotNull(message = "{phoneNo_invalid_empty}")
    @Pattern(regexp = "^(?:\\+91|91)?[6-9]\\d{9}$", message = "{phoneNo_invalid_format}")
     private String phoneNo;
    @NotNull(message = "{pgId_invalid_empty}")
    private Integer pgId;


     private Set<String> roles;
    
    @NotBlank(message = "{password_invalid_empty}")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}|;:'\",.<>?/`~]).{8,32}$", message = "{password_invalid_format}")
    private String password;
  

}
