package com.skywalker.backend.dto;

import com.skywalker.backend.model.User;
import lombok.Data;

@Data
public class DoctorDTO {
    private User user;
    private String specialization;
}
