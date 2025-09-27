package com.skywalker.backend.service.repo;

import com.skywalker.backend.model.Doctor;
import java.util.List;
import java.util.Optional;

public interface IDoctorService {
    Doctor createDoctor(Doctor doctor);
    Optional<Doctor> getDoctorById(Long id);
    List<Doctor> getAllDoctors();
    List<Doctor> getDoctorsBySpecialization(String specialization);
    void deleteDoctor(Long id);
}
