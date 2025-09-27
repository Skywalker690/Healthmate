package com.skywalker.backend.controller;

import com.skywalker.backend.exception.OurException;
import com.skywalker.backend.model.Doctor;
import com.skywalker.backend.service.repo.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        return doctorService.createDoctor(doctor);
    }

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id).orElseThrow(() -> new OurException("Doctor not found"));
    }

    @GetMapping("/specialization/{spec}")
    public List<Doctor> getDoctorsBySpecialization(@PathVariable String spec) {
        return doctorService.getDoctorsBySpecialization(spec);
    }

    @DeleteMapping("/{id}")
    public void deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
    }
}
