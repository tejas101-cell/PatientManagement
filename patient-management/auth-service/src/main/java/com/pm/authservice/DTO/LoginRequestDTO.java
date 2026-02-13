package com.pm.authservice.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @NotBlank(message = "Email is required")
    @Email(message = "Please enter the valid email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password length should be greater than 8 characters")
    private String password;
}
