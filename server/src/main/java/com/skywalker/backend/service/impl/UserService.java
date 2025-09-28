package com.skywalker.backend.service.impl;

import com.skywalker.backend.domain.GENDER;
import com.skywalker.backend.domain.USER_ROLE;
import com.skywalker.backend.dto.LoginRequest;
import com.skywalker.backend.dto.RegisterRequest;
import com.skywalker.backend.dto.Response;
import com.skywalker.backend.exception.OurException;
import com.skywalker.backend.model.Doctor;
import com.skywalker.backend.model.Patient;
import com.skywalker.backend.model.User;
import com.skywalker.backend.repository.DoctorRepository;
import com.skywalker.backend.repository.PatientRepository;
import com.skywalker.backend.repository.UserRepository;
import com.skywalker.backend.security.JwtTokenProvider;
import com.skywalker.backend.service.repo.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Transactional
    public Response register(RegisterRequest request) {
        Response response = new Response();
        try {
            // Validate password
            if (request.getPassword() == null || request.getPassword().isBlank()) {
                throw new OurException("Password cannot be null or empty");
            }

            // Check for duplicate email
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new OurException("Email already exists: " + request.getEmail());
            }

            // Create and save User first
            User user = new User();

            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setAddress(request.getAddress());
            user.setPhoneNumber(request.getPhoneNumber());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setDateOfBirth(LocalDate.parse(request.getDateOfBirth()));
            user.setGender(request.getGender() != null
                    ? GENDER.valueOf(request.getGender().toUpperCase())
                    : GENDER.MALE);

            user.setRole(request.getRole() != null ? request.getRole() : USER_ROLE.ROLE_PATIENT);

            User savedUser = userRepository.save(user); // User Persist first

            // Now handle Patient or Doctor
            if (savedUser.getRole() == USER_ROLE.ROLE_PATIENT) {
                Patient patient = new Patient();
                patient.setUser(savedUser);

                patientRepository.save(patient);
            }
            else if (savedUser.getRole() == USER_ROLE.ROLE_DOCTOR) {
                Doctor doctor = new Doctor();
                doctor.setUser(savedUser);
                doctor.setExperience(request.getExperience());
                doctor.setAvailableHours(request.getAvailableHours());
                doctor.setSpecialization(request.getSpecialization() != null ? request.getSpecialization() : "General");
                doctorRepository.save(doctor);
            }

            response.setStatusCode(200);
            response.setMessage("User registered successfully");

        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred during user registration: " + e.getMessage());
        }

        return response;
    }




    @Override
    public Response login(LoginRequest loginRequest) {

        Response response = new Response();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            var user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new OurException("User Not found")
                    );

            var token = jwtTokenProvider.generateToken(user);
            response.setStatusCode(200);
            response.setToken(token);
            response.setRole(String.valueOf(user.getRole()));
            response.setMessage("Login Successful");
        } catch (OurException e) {

            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Error Occurred During User Login " + e.getMessage());
        }
        return response;
    }




    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }




}
