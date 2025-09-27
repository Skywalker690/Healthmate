package com.skywalker.backend.model;

import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.Email;
import com.skywalker.backend.domain.USER_ROLE;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Collection;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Column(unique = true, nullable = false)
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @JsonIgnore
    @NotBlank(message = "Password is required")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private USER_ROLE role = USER_ROLE.ROLE_PATIENT;

    @CreationTimestamp
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime updatedDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role+""));
    }

    @Override
    public String getUsername() {
        return email;
    }
}
