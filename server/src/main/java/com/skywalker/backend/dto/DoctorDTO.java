package com.skywalker.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoctorDTO {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String gender;
    private String address;
    private String dateOfBirth;
    private Integer experience;
    private String specialization;
    private String availableHours;

    private UserDTO user;
    private List<AppointmentDTO> appointments;
}
