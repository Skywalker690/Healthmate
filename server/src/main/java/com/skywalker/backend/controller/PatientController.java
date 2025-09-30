package com.skywalker.backend.controller;

import com.skywalker.backend.dto.Response;
import com.skywalker.backend.model.User;
import com.skywalker.backend.service.impl.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Response getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public Response getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }

    @PutMapping("/patients/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Response updatePatient(@PathVariable Long id, @RequestBody User request) {
        return patientService.updatePatient(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Response deletePatient(@PathVariable Long id) {
        return patientService.deletePatient(id);
    }
}
