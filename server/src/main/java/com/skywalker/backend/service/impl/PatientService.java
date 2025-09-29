package com.skywalker.backend.service.impl;

import com.skywalker.backend.dto.Response;
import com.skywalker.backend.exception.OurException;
import com.skywalker.backend.model.Patient;
import com.skywalker.backend.repository.PatientRepository;
import com.skywalker.backend.repository.UserRepository;
import com.skywalker.backend.security.Utils;
import com.skywalker.backend.service.repo.IPatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService implements IPatientService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

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
    public Response getPatientById(Long id) {
        Response response = new Response();
        try {
            Patient patient =patientRepository.findById(id).orElseThrow(
                    () -> new OurException("Patient not found with this ID")
            );
            response.setPatient(Utils.mapPatientToDTO(patient));
            response.setStatusCode(200);
            response.setMessage("Patient fetched successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while fetching patients: " + e.getMessage());
        }
        return response;

    }

    @Transactional
    @Override
    public Response deletePatient(Long id) {
        Response response = new Response();
        try {
            Patient patient = patientRepository.findById(id)
                    .orElseThrow(() -> new OurException("Patient not found, deletion failed"));


            // Delete associated user and patient
            Long userId = patient.getUser().getId();
            patientRepository.deleteById(patient.getId());
            userRepository.deleteById(userId);

            response.setStatusCode(200);
            response.setMessage("Patient deleted successfully");

        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while deleting patient: " + e.getMessage());
        }
        return response;
    }

}
