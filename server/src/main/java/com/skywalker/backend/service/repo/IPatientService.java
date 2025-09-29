package com.skywalker.backend.service.repo;

import com.skywalker.backend.dto.Response;
import com.skywalker.backend.model.Patient;

import java.util.Optional;

public interface IPatientService {
    Response getPatientById(Long id);
    Response getAllPatients();
    void deletePatient(Long id);
}
