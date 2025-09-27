package com.skywalker.backend.dto;

import com.skywalker.backend.domain.STATUS;
import com.skywalker.backend.model.Doctor;
import com.skywalker.backend.model.Patient;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDTO {

    private String notes;
    private STATUS status;
    private LocalDateTime appointmentDateTime;

    private Doctor doctor;
    private Patient patient;
}
