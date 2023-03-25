package HealthAPI.service;

import HealthAPI.model.Appointment;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AppointmentService {

    Appointment createAppointment(Appointment appointment);

    Appointment findAppointmentById(Long id);

    Appointment deleteAppointmentById(Long id);

    ResponseEntity<?> restoreById(Long id);

    List<Appointment> findAllByClientId(Long clientId);

    List<Appointment> findAllByUserId(Long hcpId);

}