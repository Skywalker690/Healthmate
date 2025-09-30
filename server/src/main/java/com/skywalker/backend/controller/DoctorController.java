package com.skywalker.backend.controller;

import com.skywalker.backend.dto.Response;
import com.skywalker.backend.exception.OurException;
import com.skywalker.backend.model.Doctor;
import com.skywalker.backend.model.User;
import com.skywalker.backend.service.impl.DoctorService;
import com.skywalker.backend.service.repo.IDoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;


    @GetMapping

    public Response getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/{id}")
    public Response getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }

    @GetMapping("/specialization/{spec}")
    public Response getDoctorsBySpecialization(@PathVariable String spec) {
        return doctorService.getDoctorsBySpecialization(spec);
    }

    @PutMapping("/doctors/{id}")
    public Response updateDoctor(@PathVariable Long id, @RequestBody Doctor request) {
        return doctorService.updateDoctor(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Response deleteDoctor(@PathVariable Long id) {
        return doctorService.deleteDoctor(id);
    }
}
