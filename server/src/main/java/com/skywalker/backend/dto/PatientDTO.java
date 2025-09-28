package com.skywalker.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.skywalker.backend.domain.GENDER;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientDTO {

    private GENDER gender;
    private String address;
    private LocalDate dateOfBirth;

    private UserDTO user;
    private List<AppointmentDTO> appointments;
}
