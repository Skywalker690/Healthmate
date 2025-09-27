package com.skywalker.backend.dto;

import com.skywalker.backend.domain.USER_ROLE;
import lombok.Data;

@Data
public class RegisterRequest {

    private String name;

    private String email;

    private String password;

    private USER_ROLE role;

    // Fields for Patient
    private String gender;
    private String address;
    private String dateOfBirth;
    private String contactNumber;

    // Fields for Doctor
    private String specialization;
    private Integer experience;
    private String docNumber;
    private String availableHours;
}
