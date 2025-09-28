package com.skywalker.backend.dto;

import com.skywalker.backend.domain.STATUS;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDTO {

    private Long id;
    private String notes;
    private STATUS status;
    private String appointmentCode;
    private LocalDateTime appointmentDateTime;

    private DoctorDTO doctor;
    private PatientDTO patient;
}
