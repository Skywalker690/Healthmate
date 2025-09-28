package com.skywalker.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoctorDTO {

    private Long id;
    private Integer experience;
    private String specialization;
    private String availableHours;

    private UserDTO user;
    private List<AppointmentDTO> appointments;
}
