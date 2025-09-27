package com.skywalker.backend.controller;

import com.skywalker.backend.exception.OurException;
import com.skywalker.backend.model.Patient;
import com.skywalker.backend.service.repo.IPatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patients")
public class PatientController {

    private final IPatientService IPatientService;

    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        return IPatientService.createPatient(patient);
    }

    @GetMapping
    public List<Patient> getAllPatients() {
        return IPatientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable Long id) {
        return IPatientService.getPatientById(id).orElseThrow(
                () -> new OurException("Patient not found")
        );
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id) {
        IPatientService.deletePatient(id);
    }
}
