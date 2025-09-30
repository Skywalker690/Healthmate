package com.skywalker.backend.controller;

import com.skywalker.backend.dto.Response;
import com.skywalker.backend.model.Doctor;
import com.skywalker.backend.service.impl.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping
    public ResponseEntity<Response> getAllDoctors() {
        Response response = doctorService.getAllDoctors();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getDoctorById(@PathVariable Long id) {
        Response response = doctorService.getDoctorById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/specialization/{spec}")
    public ResponseEntity<Response> getDoctorsBySpecialization(@PathVariable String spec) {
        Response response = doctorService.getDoctorsBySpecialization(spec);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateDoctor(@PathVariable Long id, @RequestBody Doctor request) {
        Response response = doctorService.updateDoctor(id, request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Response> deleteDoctor(@PathVariable Long id) {
        Response response = doctorService.deleteDoctor(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
