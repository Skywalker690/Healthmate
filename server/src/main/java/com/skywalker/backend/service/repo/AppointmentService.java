package com.skywalker.backend.service.repo;

import com.skywalker.backend.model.Appointment;
import com.skywalker.backend.dto.AppointmentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AppointmentService {

    Appointment getAppointmentById(Long id);

    List<Appointment> getAllAppointments();

    Appointment createAppointment(AppointmentDTO dto);

    Appointment updateAppointment(Long id, AppointmentDTO dto);

    void deleteAppointment(Long id);
}
