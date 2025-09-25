package com.skywalker.backend.service.repo;

import com.skywalker.backend.model.Doctor;
import com.skywalker.backend.dto.DoctorDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DoctorService {

    Doctor getDoctorById(Long id);

    List<Doctor> getAllDoctors();

    Doctor createDoctor(DoctorDTO dto);

    Doctor updateDoctor(Long id, DoctorDTO dto);

    void deleteDoctor(Long id);
}
