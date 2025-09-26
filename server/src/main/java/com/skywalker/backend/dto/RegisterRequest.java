package com.skywalker.backend.dto;

import com.skywalker.backend.domain.USER_ROLE;
import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private USER_ROLE role;
}
