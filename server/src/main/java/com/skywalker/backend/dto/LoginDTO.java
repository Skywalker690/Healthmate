package com.skywalker.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {

    @NotBlank(message = "Email Required ")
    private String email;

    @NotBlank(message = "Password Required ")
    private String password;
}
