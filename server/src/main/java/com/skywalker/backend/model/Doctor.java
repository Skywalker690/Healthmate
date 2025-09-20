package com.skywalker.backend.model;

import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.List;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // One-to-One with User
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String specialization;
    private Integer experience; // in years
    private String availableHours; // e.g., "Mon-Fri 9AM-5PM"
    private String contactNumber;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // One doctor -> Many appointments
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Appointment> appointments;
}
