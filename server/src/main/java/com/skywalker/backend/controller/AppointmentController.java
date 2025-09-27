package com.skywalker.backend.controller;

import com.skywalker.backend.exception.OurException;
import com.skywalker.backend.model.Appointment;
import com.skywalker.backend.service.repo.IAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final IAppointmentService IAppointmentService;

    @PostMapping
    public Appointment createAppointment(@RequestBody Appointment appointment) {
        return IAppointmentService.createAppointment(appointment);
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return IAppointmentService.getAllAppointments();
    }

    @GetMapping("/{id}")
    public Appointment getAppointmentById(@PathVariable Long id) {
        return IAppointmentService.getAppointmentById(id).orElseThrow(() -> new OurException("Appointment not found"));
    }

    @GetMapping("/doctor/{doctorId}")
    public List<Appointment> getAppointmentsByDoctor(@PathVariable Long doctorId) {
        return IAppointmentService.getAppointmentsByDoctor(doctorId);
    }

    @GetMapping("/patient/{patientId}")
    public List<Appointment> getAppointmentsByPatient(@PathVariable Long patientId) {
        return IAppointmentService.getAppointmentsByPatient(patientId);
    }

    @PutMapping("/{id}/status/{status}")
    public Appointment updateAppointmentStatus(@PathVariable Long id, @PathVariable String status) {
        return IAppointmentService.updateAppointmentStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        IAppointmentService.deleteAppointment(id);
    }
}
