package com.skywalker.backend.service.impl;

import com.skywalker.backend.domain.USER_ROLE;
import com.skywalker.backend.dto.LoginRequest;
import com.skywalker.backend.dto.Response;
import com.skywalker.backend.exception.OurException;
import com.skywalker.backend.model.Doctor;
import com.skywalker.backend.model.Patient;
import com.skywalker.backend.model.User;
import com.skywalker.backend.repository.DoctorRepository;
import com.skywalker.backend.repository.PatientRepository;
import com.skywalker.backend.repository.UserRepository;
import com.skywalker.backend.security.JwtTokenProvider;
import com.skywalker.backend.service.repo.IDoctorService;
import com.skywalker.backend.service.repo.IPatientService;
import com.skywalker.backend.service.repo.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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


    public Response register(User user) {
        Response response = new Response();
        try {
            // Check if email already exists
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new OurException(user.getEmail() + " already exists");
            }

            // Encode password
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(USER_ROLE.valueOf(user.getRole().name()));

            User savedUser = userRepository.save(user);
            switch (savedUser.getRole()) {
                case ROLE_PATIENT:
                    Patient patient = new Patient();
                    patient.setUser(savedUser);
                    patientRepository.save(patient);
                    break;
                case ROLE_DOCTOR:
                    Doctor doctor = new Doctor();
                    doctor.setUser(savedUser);
                    doctorRepository.save(doctor);
                    break;
                case ROLE_ADMIN:
                    break;
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
            var user = userRepository.findByEmail(loginRequest.getEmail()).
                    orElseThrow(
                            () -> new OurException("User Not Registered")
                    );
            var token = jwtTokenProvider.generateToken((Authentication) user);

            response.setMessage("Login Successful");
            response.setStatusCode(200);
            response.setToken(token);
            response.setRole(String.valueOf(user.getRole()));

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Occurred During USer Login " + e.getMessage());
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
