package com.skywalker.backend.controller;

import com.skywalker.backend.dto.Response;
import com.skywalker.backend.model.Appointment;
import com.skywalker.backend.service.repo.IAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final IAppointmentService IAppointmentService;

    @PostMapping
    public Response createAppointment(@RequestBody Appointment appointment) {
        return IAppointmentService.createAppointment(appointment);
    }

    @GetMapping
    public Response getAllAppointments() {
        return IAppointmentService.getAllAppointments();
    }

    @GetMapping("/{id}")
    public Response getAppointmentById(@PathVariable Long id) {
        return IAppointmentService.getAppointmentById(id);
    }

    @GetMapping("/doctor/{doctorId}")
    public Response getAppointmentsByDoctor(@PathVariable Long doctorId) {
        return IAppointmentService.getAppointmentsByDoctor(doctorId);
    }

    @GetMapping("/patient/{patientId}")
    public Response getAppointmentsByPatient(@PathVariable Long patientId) {
        return IAppointmentService.getAppointmentsByPatient(patientId);
    }

    @PutMapping("/{id}/status/{status}")
    public Response updateAppointmentStatus(@PathVariable Long id, @PathVariable String status) {
        return IAppointmentService.updateAppointmentStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public Response deleteAppointment(@PathVariable Long id) {
        return IAppointmentService.deleteAppointment(id);
    }
}

