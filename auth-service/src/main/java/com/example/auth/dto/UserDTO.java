package com.example.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserDTO {
    @Size(min = 5, max = 255, message = "username should contain 5-255 characters")
    @NotBlank(message = "username cannot be empty!")
    private String username;

    @Size(max = 30, message = "Password cannot contain more than 30 characters")
    @NotBlank(message = "Password cannot be empty")
    private String password;
}