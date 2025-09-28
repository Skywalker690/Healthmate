package com.skywalker.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.skywalker.backend.model.Appointment;
import com.skywalker.backend.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoctorDTO {

    private Integer experience;
    private String specialization;
    private String availableHours;

    private User user;
    private List<Appointment> appointments;
}
