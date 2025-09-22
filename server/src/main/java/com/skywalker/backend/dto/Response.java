package com.skywalker.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.skywalker.backend.domain.USER_ROLE;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private USER_ROLE role; // ROLE_DOCTOR or ROLE_PATIENT
}
