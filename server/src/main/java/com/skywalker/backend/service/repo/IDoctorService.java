package com.skywalker.backend.service.repo;

import com.skywalker.backend.dto.Response;
import com.skywalker.backend.model.Doctor;
import java.util.List;

public interface IDoctorService {

    Response getAllDoctors();

    Response getDoctorById(Long id);

    Response deleteDoctor(Long id);

    Response getDoctorsBySpecialization(String specialization);

}
