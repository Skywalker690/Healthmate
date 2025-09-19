package com.skywalker.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.skywalker.backend.domain.USER_ROLE;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name Required")
    private String name;

    @Column(unique = true)
    @NotBlank(message = "Email Required")
    private String email;

    @NotBlank(message = "Password Required")
    @JsonIgnore
    private String password;

    @NotBlank(message = "Phone Number Required")
    private String phoneNumber;

    @Column(updatable = false)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updateDate;

    @Enumerated(EnumType.STRING)
    private USER_ROLE role = USER_ROLE.ROLE_PATIENT;

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
    }
}
