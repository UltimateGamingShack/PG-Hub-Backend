package com.pghub.user.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
 @Getter
 @Setter
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotNull
     private Character gender;
    @NotNull
     private Integer roomNo;
    @NotNull
     private String phoneNo;


     private Set<String> roles;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
  

}
