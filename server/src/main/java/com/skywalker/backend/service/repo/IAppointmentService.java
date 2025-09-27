package com.skywalker.backend.service.repo;

import com.skywalker.backend.model.Appointment;
import java.util.List;
import java.util.Optional;

public interface IAppointmentService {
    Appointment createAppointment(Appointment appointment);
    Optional<Appointment> getAppointmentById(Long id);
    List<Appointment> getAppointmentsByDoctor(Long doctorId);
    List<Appointment> getAppointmentsByPatient(Long patientId);
    List<Appointment> getAllAppointments();
    Appointment updateAppointmentStatus(Long id, String status);
    void deleteAppointment(Long id);
}
