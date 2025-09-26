package com.skywalker.backend.dto;

import com.skywalker.backend.domain.STATUS;
import com.skywalker.backend.model.Doctor;
import com.skywalker.backend.model.Patient;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDTO {
    private Doctor doctor;
    private Patient patient;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime appointmentDateTime;

    private String reason; // optional: reason for appointment
    private STATUS status = STATUS.SCHEDULED; // default
}
