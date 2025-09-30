package com.skywalker.backend.service.impl;

import com.skywalker.backend.domain.GENDER;
import com.skywalker.backend.domain.USER_ROLE;
import com.skywalker.backend.dto.*;
import com.skywalker.backend.exception.OurException;
import com.skywalker.backend.model.Doctor;
import com.skywalker.backend.model.Patient;
import com.skywalker.backend.model.User;
import com.skywalker.backend.repository.DoctorRepository;
import com.skywalker.backend.repository.PatientRepository;
import com.skywalker.backend.repository.UserRepository;
import com.skywalker.backend.security.CustomUserDetails;
import com.skywalker.backend.security.JwtTokenProvider;
import com.skywalker.backend.security.Utils;
import com.skywalker.backend.service.repo.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Transactional
    @Override
    public Response register(RegisterRequest request) {
        Response response = new Response();
        try {
            // Validate password
            if (request.getPassword() == null || request.getPassword().isBlank()) {
                throw new OurException("Password cannot be null or empty");
            }

            // Validate email
            String email = request.getEmail().trim();
            if (userRepository.existsByEmail(email)) {
                throw new OurException("Email already exists: " + email);
            }

            // Create User
            User user = new User();
            user.setName(request.getName());
            user.setEmail(email);
            user.setAddress(request.getAddress());
            user.setPhoneNumber(request.getPhoneNumber());
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            // Date of birth
            if (request.getDateOfBirth() != null && !request.getDateOfBirth().isBlank()) {
                user.setDateOfBirth(LocalDate.parse(request.getDateOfBirth()));
            }

            // Gender
            try {
                user.setGender(request.getGender() != null
                        ? GENDER.valueOf(request.getGender().toUpperCase())
                        : GENDER.MALE);
            } catch (IllegalArgumentException ex) {
                throw new OurException("Invalid gender value: " + request.getGender());
            }

            // Role
            user.setRole(request.getRole() != null ? request.getRole() : USER_ROLE.ROLE_PATIENT);

            User savedUser = userRepository.save(user);

            // Create Patient or Doctor entity
            if (savedUser.getRole() == USER_ROLE.ROLE_PATIENT) {
                Patient patient = new Patient();
                patient.setUser(savedUser);
                patientRepository.save(patient);
            } else if (savedUser.getRole() == USER_ROLE.ROLE_DOCTOR) {
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
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new OurException("User not found"));

            String token = jwtTokenProvider.generateToken(user);

            response.setStatusCode(200);
            response.setToken(token);
            response.setRole(user.getRole().name());
            response.setMessage("Login successful");

        } catch (BadCredentialsException e) {
            response.setStatusCode(401);
            response.setMessage("Invalid email or password");
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred during login: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllUsers() {
        Response response = new Response();
        try {
            List<User> users = userRepository.findAll();
            response.setUserList(Utils.mapUserListToDTOList(users));
            response.setStatusCode(200);
            response.setMessage("Users fetched successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while fetching users: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserById(Long id) {
        Response response = new Response();
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new OurException("User not found"));

            response.setUser(Utils.mapUserToDTO(user));
            response.setStatusCode(200);
            response.setMessage("User fetched successfully");

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while fetching user: " + e.getMessage());
        }
        return response;
    }

    @Transactional
    @Override
    public Response deleteUser(Long id) {
        Response response = new Response();
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new OurException("User not found, deletion failed"));

            if (user.getRole() == USER_ROLE.ROLE_DOCTOR) {
                doctorRepository.deleteByUserId(id);
                response.setMessage("Doctor record deleted");
            } else if (user.getRole() == USER_ROLE.ROLE_PATIENT) {
                patientRepository.deleteByUserId(id);
                response.setMessage("Patient record deleted");
            }

            userRepository.delete(user);
            response.setStatusCode(200);
            response.setMessage(response.getMessage() + " and user deleted successfully");

        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while deleting user: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response changePassword(PasswordChangeRequest request) {
        Response response = new Response();
        try {
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();

            User user = userRepository.findById(userDetails.getId())
                    .orElseThrow(() -> new OurException("User not found"));

            if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
                throw new OurException("Old password is incorrect");
            }

            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);
            response.setMessage("Password changed successfully ");
            response.setStatusCode(200);
        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while deleting user: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getCurrentUser() {
        Response response = new Response();
        try {
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();

            User user = userRepository.findById(userDetails.getId())
                    .orElseThrow(() -> new OurException("User not found"));

            response.setStatusCode(200);
            response.setMessage("Fetch Success");
            response.setUser(Utils.mapUserToDTO(user));
        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting current user: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response updateCurrentUser(User updatedUser) {
        Response response = new Response();
        try {
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();

            User user = userRepository.findById(userDetails.getId())
                    .orElseThrow(() -> new OurException("User not found"));

            user.setName(updatedUser.getName());
            user.setAddress(updatedUser.getAddress());
            user.setPhoneNumber(updatedUser.getPhoneNumber());
            user.setDateOfBirth(updatedUser.getDateOfBirth());
            userRepository.save(user);

            response.setStatusCode(200);
            response.setMessage("User updated successfully");
            response.setUser(Utils.mapUserToDTO(user));

        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting current user: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getUsersByRole(String role) {
        Response response = new Response();
        try {
            List<UserDTO> users = userRepository.findAll()
                    .stream()
                    .filter(u -> u.getRole().name().equalsIgnoreCase(role))
                    .map(Utils::mapUserToDTO)
                    .toList();

            response.setStatusCode(200);
            response.setMessage("User fetched successfully");
            response.setUserList(users);
        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting current users by role: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateUser(Long id, User updatedUser) {
        Response response = new Response();
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new OurException("User not found with id " + id));

            user.setName(updatedUser.getName());
            user.setRole(updatedUser.getRole());
            user.setAddress(updatedUser.getAddress());
            user.setPhoneNumber(updatedUser.getPhoneNumber());
            user.setDateOfBirth(updatedUser.getDateOfBirth());

            userRepository.save(user);
            response.setStatusCode(400);
            response.setMessage("User Updated ");
            response.setUser(Utils.mapUserToDTO(user));
        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting current users by role: " + e.getMessage());
        }
        return response;
    }
}
