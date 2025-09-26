package com.skywalker.backend.repository;

import com.skywalker.backend.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository  extends JpaRepository<Appointment,Long> {
    List<Appointment> findByPatientId(Long patientId);

    List<Appointment> findByDoctorId(Long doctorId);
}
