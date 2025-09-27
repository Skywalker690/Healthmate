package com.skywalker.backend.controller;

import com.skywalker.backend.domain.USER_ROLE;
import com.skywalker.backend.dto.LoginRequest;
import com.skywalker.backend.dto.LoginResponse;
import com.skywalker.backend.dto.RegisterRequest;
import com.skywalker.backend.model.Doctor;
import com.skywalker.backend.model.Patient;
import com.skywalker.backend.model.User;
import com.skywalker.backend.security.JwtTokenProvider;
import com.skywalker.backend.service.repo.DoctorService;
import com.skywalker.backend.service.repo.PatientService;
import com.skywalker.backend.service.repo.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    private final UserService userService;
    private final DoctorService doctorService;
    private final PatientService patientService;

    // Register User
    @PostMapping("/register")
    public User registerUser(@RequestBody RegisterRequest registerRequest) {
        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(registerRequest.getRole());

        User savedUser = userService.createUser(user);

        if (savedUser.getRole() == USER_ROLE.ROLE_PATIENT) {
            Patient patient = new Patient();
            patient.setUser(savedUser);
            patientService.createPatient(patient);
        }
        else if (savedUser.getRole() == USER_ROLE.ROLE_DOCTOR) {
            Doctor doctor = new Doctor();
            doctor.setUser(savedUser);
            doctorService.createDoctor(doctor);
        }

        return savedUser;
    }


    // Login User
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        String token = jwtTokenProvider.generateToken(authentication);
        return new LoginResponse(token, "Login successful!",true);
    }
}
