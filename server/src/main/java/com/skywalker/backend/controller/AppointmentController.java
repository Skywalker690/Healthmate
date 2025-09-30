package com.skywalker.backend.controller;

import com.skywalker.backend.dto.Response;
import com.skywalker.backend.model.Appointment;
import com.skywalker.backend.service.impl.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Response getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @PostMapping("/{patientId}/{doctorId}")
    public Response createAppointment(@PathVariable Long patientId,
                                      @PathVariable Long doctorId,
                                      @RequestBody Appointment appointment) {
        return appointmentService.createAppointment(patientId,doctorId,appointment);
    }

    @GetMapping("/{id}")
    public Response getAppointmentById(@PathVariable Long id) {
        return appointmentService.getAppointmentById(id);
    }

    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Response getAppointmentsByDoctor(@PathVariable Long doctorId) {
        return appointmentService.getAppointmentsByDoctor(doctorId);
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Response getAppointmentsByPatient(@PathVariable Long patientId) {
        return appointmentService.getAppointmentsByPatient(patientId);
    }

    @PutMapping("/{id}/status/{status}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Response updateAppointmentStatus(@PathVariable Long id, @PathVariable String status) {
        return appointmentService.updateAppointmentStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public Response deleteAppointment(@PathVariable Long id) {
        return appointmentService.deleteAppointment(id);
    }
}

