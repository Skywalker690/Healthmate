package com.skywalker.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.skywalker.backend.domain.GENDER;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientDTO {

    private Long id;            // Patient ID
    private String name;        // From User
    private String email;       // From User
    private String phoneNumber; // From User
    private String gender;      // From User
    private String address;     // From Patient
    private String dateOfBirth; // From Patient
    private String role;        // From User
    private List<AppointmentDTO> appointments; // Keep if needed
}

