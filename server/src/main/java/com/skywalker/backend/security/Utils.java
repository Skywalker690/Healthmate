package com.skywalker.backend.security;

import com.skywalker.backend.dto.*;
import com.skywalker.backend.model.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Utils {

    // ------------------- APPOINTMENT -------------------

    public static AppointmentDTO mapAppointmentToDTO(Appointment appointment) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(appointment.getId());
        dto.setNotes(appointment.getNotes());
        dto.setStatus(appointment.getStatus());
        dto.setAppointmentDateTime(appointment.getAppointmentDateTime());
        dto.setAppointmentCode(appointment.getAppointmentCode());

        if (appointment.getDoctor() != null) {
            dto.setDoctor(mapDoctorToDTO(appointment.getDoctor()));
        }

        if (appointment.getPatient() != null) {
            dto.setPatient(mapPatientToDTO(appointment.getPatient()));
        }

        return dto;
    }

    public static List<AppointmentDTO> mapAppointmentListToDTOList(List<Appointment> appointments) {
        return appointments.stream()
                .map(Utils::mapAppointmentToDTO)
                .collect(Collectors.toList());
    }

    // ------------------- DOCTOR -------------------

    public static DoctorDTO mapDoctorToDTO(Doctor doctor) {
        User user = doctor.getUser();
        DoctorDTO dto = new DoctorDTO();

        dto.setId(doctor.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setGender(user.getGender().name());
        dto.setAddress(user.getAddress());
        dto.setDateOfBirth(String.valueOf(user.getDateOfBirth()));

        dto.setExperience(doctor.getExperience());
        dto.setSpecialization(doctor.getSpecialization());
        dto.setAvailableHours(doctor.getAvailableHours());

        return dto;
    }

    public static DoctorDTO mapDoctorToDTOWithAppointments(Doctor doctor) {
        DoctorDTO dto = mapDoctorToDTO(doctor);

        if (doctor.getAppointments() != null) {
            dto.setAppointments(
                    doctor.getAppointments().stream()
                            .map(Utils::mapAppointmentToDTO)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public static List<DoctorDTO> mapDoctorListToDTOList(List<Doctor> doctors) {
        return doctors.stream().map(Utils::mapDoctorToDTO).toList();
    }

    // ------------------- PATIENT -------------------

    public static PatientDTO mapPatientToDTO(Patient patient) {
        User user = patient.getUser();
        PatientDTO dto = new PatientDTO();

        dto.setId(patient.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setGender(user.getGender().name());
        dto.setAddress(user.getAddress());
        dto.setDateOfBirth(String.valueOf(user.getDateOfBirth()));

        return dto;
    }

    public static PatientDTO mapPatientToDTOWithAppointments(Patient patient) {
        PatientDTO dto = mapPatientToDTO(patient);

        if (patient.getAppointments() != null) {
            dto.setAppointments(
                    patient.getAppointments().stream()
                            .map(Utils::mapAppointmentToDTO)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public static List<PatientDTO> mapPatientListToDTOList(List<Patient> patients) {
        return patients.stream().map(Utils::mapPatientToDTO).toList();
    }

    // ------------------- USER -------------------

    public static UserDTO mapUserToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setGender(user.getGender());
        dto.setRole(user.getRole());
        return dto;
    }

    public static List<UserDTO> mapUserListToDTOList(List<User> users) {
        return users.stream().map(Utils::mapUserToDTO).toList();
    }

    // ------------------- APPOINTMENT CODE GENERATOR -------------------

    public static String generateAppointmentCode(int length) {
        String code = "APT-" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
        return code.substring(0, Math.min(length + 4, code.length())); // +4 for "APT-"
    }
}
