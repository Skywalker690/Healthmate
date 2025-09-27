package com.skywalker.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.skywalker.backend.domain.STATUS;
import com.skywalker.backend.model.Appointment;
import com.skywalker.backend.model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientDTO {

    private STATUS gender;
    private String address;
    private String contactNumber;
    private LocalDate dateOfBirth;

    private User user;
    private List<Appointment> appointments;
}
