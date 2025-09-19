package com.skywalker.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // One-to-One with User
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender; // MALE, FEMALE, OTHER

    private String contactNumber;
    private String address;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // One patient -> Many appointments
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Appointment> appointments;

    public enum Gender {
        MALE, FEMALE, OTHER
    }
}
