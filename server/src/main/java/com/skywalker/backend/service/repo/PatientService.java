package com.skywalker.backend.service.repo;

import com.skywalker.backend.model.Patient;
import com.skywalker.backend.dto.PatientDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PatientService {

    Patient getPatientById(Long id);

    List<Patient> getAllPatients();

    Patient createPatient(PatientDTO dto);

    Patient updatePatient(Long id, PatientDTO dto);

    void deletePatient(Long id);
}
