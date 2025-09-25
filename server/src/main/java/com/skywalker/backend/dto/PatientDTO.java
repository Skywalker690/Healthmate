package com.skywalker.backend.dto;

import com.skywalker.backend.model.User;
import lombok.Data;

@Data
public class PatientDTO {
    private User user;
    private Integer age;
}
