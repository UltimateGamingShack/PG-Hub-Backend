package com.pghub.user.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
	@NotBlank(message = "{username_invalid_empty}")
	@Size(min = 3, max = 20, message = "{username_invalid_size}")
	private String username;

	@NotBlank(message = "{password_invalid_empty}")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}|;:'\",.<>?/`~]).{8,32}$", message = "{password_invalid_format}")
	private String password;

}
