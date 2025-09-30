package com.skywalker.backend.service.impl;

import com.skywalker.backend.dto.DoctorDTO;
import com.skywalker.backend.dto.Response;
import com.skywalker.backend.exception.OurException;
import com.skywalker.backend.model.Doctor;
import com.skywalker.backend.model.User;
import com.skywalker.backend.repository.DoctorRepository;
import com.skywalker.backend.repository.UserRepository;
import com.skywalker.backend.security.Utils;
import com.skywalker.backend.service.repo.IDoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService implements IDoctorService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;

    @Override
    public Response getAllDoctors() {
        Response response = new Response();
        try {
            List<Doctor> patients = doctorRepository.findAll();
            response.setDoctorList(Utils.mapDoctorListToDTOList(patients));
            response.setStatusCode(200);
            response.setMessage("Doctor fetched successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while fetching doctor: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getDoctorById(Long id) {
        Response response = new Response();
        try {
            Doctor doctor =doctorRepository.findById(id).orElseThrow(
                    () -> new OurException("Doctor not found with this ID")
            );
            response.setDoctor(Utils.mapDoctorToDTO(doctor));
            response.setStatusCode(200);
            response.setMessage("Doctor fetched successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while fetching doctor: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getDoctorsBySpecialization(String specialization) {
        Response response = new Response();
        try {
            List<Doctor> doctors = doctorRepository.findBySpecialization(specialization);

            if (doctors.isEmpty()) {
                throw new OurException("No doctors found with specialization: " + specialization);
            }

            // Convert to DTO List
            List<DoctorDTO> doctorDTOs = Utils.mapDoctorListToDTOList(doctors);

            response.setDoctorList(doctorDTOs);
            response.setStatusCode(200);
            response.setMessage("Doctors fetched successfully");

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateDoctor(Long doctorId, Doctor request) {
        Response response = new Response();
        try {
            // Fetch doctor and associated user
            Doctor doctor = doctorRepository.findById(doctorId)
                    .orElseThrow(() -> new OurException("Doctor not found"));
            User user = doctor.getUser();

            // Update Doctor-specific fields
            if (request.getSpecialization() != null) doctor.setSpecialization(request.getSpecialization());
            if (request.getExperience() != null) doctor.setExperience(request.getExperience());
            if (request.getAvailableHours() != null) doctor.setAvailableHours(request.getAvailableHours());

            doctorRepository.save(doctor);

            response.setStatusCode(200);
            response.setMessage("Doctor updated successfully");
            response.setDoctor(Utils.mapDoctorToDTO(doctor));

        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while updating doctor: " + e.getMessage());
        }
        return response;
    }



    @Override
    public Response deleteDoctor(Long id) {
        Response response = new Response();
        try {
            Doctor doctor = doctorRepository.findById(id)
                    .orElseThrow(() -> new OurException("Doctor not found, deletion failed"));

            // Delete associated user and patient
            Long userId = doctor.getUser().getId();
            doctorRepository.deleteById(doctor.getId());
            userRepository.deleteById(userId);

            response.setStatusCode(200);
            response.setMessage("Doctor deleted successfully");

        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while deleting doctor: " + e.getMessage());
        }
        return response;
    }
}
