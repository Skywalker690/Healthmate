package com.skywalker.backend.model;

import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.skywalker.backend.domain.STATUS;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many appointments -> One doctor
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    // Many appointments -> One patient
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

    @Enumerated(EnumType.STRING)
    private STATUS status = STATUS.SCHEDULED;

    private String notes; // optional for extra info

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


}
