package com.skywalker.backend.dto;

import com.skywalker.backend.domain.STATUS;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDTO {

    private String notes;
    private STATUS status;
    private LocalDateTime appointmentDateTime;

    private DoctorDTO doctor;
    private PatientDTO patient;
}
