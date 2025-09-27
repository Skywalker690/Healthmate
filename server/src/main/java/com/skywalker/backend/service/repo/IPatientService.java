package com.skywalker.backend.service.repo;

import com.skywalker.backend.model.Patient;
import java.util.List;
import java.util.Optional;

public interface IPatientService {
    Patient createPatient(Patient patient);
    Optional<Patient> getPatientById(Long id);
    List<Patient> getAllPatients();
    void deletePatient(Long id);
}
