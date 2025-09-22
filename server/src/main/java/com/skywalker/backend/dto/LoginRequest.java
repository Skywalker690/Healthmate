package com.skywalker.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Email Required ")
    private String email;

    @NotBlank(message = "Password Required ")
    private String password;
}
