package com.skywalker.backend.service.impl;

import com.skywalker.backend.dto.AppointmentDTO;
import com.skywalker.backend.dto.Response;
import com.skywalker.backend.exception.OurException;
import com.skywalker.backend.model.Appointment;
import com.skywalker.backend.model.Doctor;
import com.skywalker.backend.model.Patient;
import com.skywalker.backend.repository.AppointmentRepository;
import com.skywalker.backend.repository.DoctorRepository;
import com.skywalker.backend.repository.PatientRepository;
import com.skywalker.backend.security.Utils;
import com.skywalker.backend.service.repo.IAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService implements IAppointmentService {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;


    @Override
    public Response createAppointment(Long patientId,
                                      Long doctorId,
                                      Appointment appointmentRequest) {
        Response response = new Response();
        try {
            // Fetch Doctor and Patient first
            Doctor doctor = doctorRepository.findById(doctorId)
                    .orElseThrow(() -> new OurException("Doctor not found"));
            Patient patient = patientRepository.findById(patientId)
                    .orElseThrow(() -> new OurException("Patient not found"));

            // Check if doctor is available at a given dateTime
            if (!isDoctorAvailable(doctorId, appointmentRequest.getAppointmentDateTime())) {
                response.setStatusCode(400);
                response.setMessage("Doctor is not available at the selected time");
                return response;
            }

            // Set doctor and patient in Appointment entity
            appointmentRequest.setDoctor(doctor);
            appointmentRequest.setPatient(patient);

            // Generate Appointment Code if null
            if (appointmentRequest.getAppointmentCode() == null || appointmentRequest.getAppointmentCode().isEmpty()) {
                appointmentRequest.setAppointmentCode(Utils.generateAppointmentCode(10));
            }

            Appointment savedAppointment = appointmentRepository.save(appointmentRequest);
            AppointmentDTO appointmentDTO = Utils.mapAppointmentToDTO(savedAppointment);

            response.setStatusCode(200);
            response.setMessage("Appointment created successfully");
            response.setAppointment(appointmentDTO);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error creating appointment: " + e.getMessage());
        }
        return response;
    }


    @Override
    public Response getAppointmentById(Long id) {
        Response response = new Response();
        try {
            Appointment appointment = appointmentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Appointment not found"));

            AppointmentDTO appointmentDTO = Utils.mapAppointmentToDTO(appointment);
            response.setStatusCode(200);
            response.setMessage("Appointment found");
            response.setAppointment(appointmentDTO);

        } catch (Exception e) {
            response.setStatusCode(404);
            response.setMessage("Error fetching appointment: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAppointmentsByDoctor(Long doctorId) {
        Response response = new Response();
        try {
            List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);
            List<AppointmentDTO> appointmentDTOList = appointments.stream()
                    .map(Utils::mapAppointmentToDTO)
                    .collect(Collectors.toList());

            response.setStatusCode(200);
            response.setMessage("Appointments fetched successfully");
            response.setAppointmentList(appointmentDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error fetching appointments: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAppointmentsByPatient(Long patientId) {
        Response response = new Response();
        try {
            List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);
            List<AppointmentDTO> appointmentDTOList = appointments.stream()
                    .map(Utils::mapAppointmentToDTO)
                    .collect(Collectors.toList());

            response.setStatusCode(200);
            response.setMessage("Appointments fetched successfully");
            response.setAppointmentList(appointmentDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error fetching appointments: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllAppointments() {
        Response response = new Response();
        try {
            List<Appointment> appointments = appointmentRepository.findAll();
            List<AppointmentDTO> appointmentDTOList = appointments.stream()
                    .map(Utils::mapAppointmentToDTO)
                    .collect(Collectors.toList());

            response.setStatusCode(200);
            response.setMessage("All appointments fetched successfully");
            response.setAppointmentList(appointmentDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error fetching all appointments: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateAppointmentStatus(Long id, String status) {
        Response response = new Response();
        try {
            Appointment appointment = appointmentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Appointment not found"));

            appointment.setStatus(Enum.valueOf(com.skywalker.backend.domain.STATUS.class, status));
            Appointment updatedAppointment = appointmentRepository.save(appointment);

            AppointmentDTO appointmentDTO = Utils.mapAppointmentToDTO(updatedAppointment);
            response.setStatusCode(200);
            response.setMessage("Appointment status updated successfully");
            response.setAppointment(appointmentDTO);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error updating appointment: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteAppointment(Long id) {
        Response response = new Response();
        try {
            appointmentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Appointment not found"));

            appointmentRepository.deleteById(id);
            response.setStatusCode(200);
            response.setMessage("Appointment deleted successfully");

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting appointment: " + e.getMessage());
        }
        return response;
    }


    public Response getAppointmentByCode(String appointmentCode) {
        Response response = new Response();
        try {
            Appointment appointment = appointmentRepository.findByAppointmentCode(appointmentCode)
                    .orElseThrow(() -> new RuntimeException("Appointment not found"));

            response.setStatusCode(200);
            response.setAppointment(Utils.mapAppointmentToDTO(appointment));
        } catch (RuntimeException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error fetching appointment: " + e.getMessage());
        }
        return response;
    }




    // Helper
    private boolean isDoctorAvailable(Long doctorId, LocalDateTime appointmentDateTime) {
        List<Appointment> existingAppointments = appointmentRepository.findByDoctorIdAndAppointmentDateTime(doctorId, appointmentDateTime);
        return existingAppointments.isEmpty();
    }
}
