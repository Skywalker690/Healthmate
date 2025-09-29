package com.skywalker.backend.service.repo;

import com.skywalker.backend.dto.Response;
import com.skywalker.backend.model.Appointment;

public interface IAppointmentService {

    Response createAppointment(Long patientId, Long doctorId, Appointment appointmentRequest);

    Response getAppointmentById(Long id);

    Response getAppointmentsByDoctor(Long doctorId);

    Response getAppointmentsByPatient(Long patientId);

    Response getAllAppointments();

    Response updateAppointmentStatus(Long id, String status);

    Response deleteAppointment(Long id);
}
