package com.skywalker.backend.controller;

import com.skywalker.backend.exception.OurException;
import com.skywalker.backend.model.Doctor;
import com.skywalker.backend.service.repo.IDoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/doctors")
public class DoctorController {

    private final IDoctorService IDoctorService;

    @PostMapping
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        return IDoctorService.createDoctor(doctor);
    }

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return IDoctorService.getAllDoctors();
    }

    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable Long id) {
        return IDoctorService.getDoctorById(id).orElseThrow(() -> new OurException("Doctor not found"));
    }

    @GetMapping("/specialization/{spec}")
    public List<Doctor> getDoctorsBySpecialization(@PathVariable String spec) {
        return IDoctorService.getDoctorsBySpecialization(spec);
    }

    @DeleteMapping("/{id}")
    public void deleteDoctor(@PathVariable Long id) {
        IDoctorService.deleteDoctor(id);
    }
}
