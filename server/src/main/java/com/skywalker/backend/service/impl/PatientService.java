package com.skywalker.backend.service.impl;

import com.skywalker.backend.dto.Response;
import com.skywalker.backend.model.Patient;
import com.skywalker.backend.repository.PatientRepository;
import com.skywalker.backend.security.Utils;
import com.skywalker.backend.service.repo.IPatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService implements IPatientService {

    private final PatientRepository patientRepository;

    @Override
    public Response getAllPatients() {
        Response response = new Response();
        try {
            List<Patient> patients = patientRepository.findAll();
            response.setPatientList(Utils.mapPatientListToDTOList(patients));
            response.setStatusCode(200);
            response.setMessage("Patient fetched successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while fetching patients: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    @Override
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
}
