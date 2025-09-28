package com.skywalker.backend.dto;

import com.skywalker.backend.domain.GENDER;
import com.skywalker.backend.domain.USER_ROLE;
import lombok.Data;

@Data
public class UserDTO {

    private String name;
    private String email;
    private String phoneNumber;

    private USER_ROLE role = USER_ROLE.ROLE_PATIENT;
    private GENDER gender = GENDER.MALE;
}
