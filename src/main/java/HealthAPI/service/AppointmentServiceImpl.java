package HealthAPI.service;

import HealthAPI.model.Appointment;
import HealthAPI.model.Status;
import HealthAPI.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public Appointment findAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow();
        return appointment;
    }

    public Appointment deleteAppointmentById(Long id) {
        Appointment fetchedAppointment = findAppointmentById(id);
        if (fetchedAppointment != null) {
            fetchedAppointment.setSTATUS(Status.INACTIVE);
            appointmentRepository.save(fetchedAppointment);
            return fetchedAppointment;
        }
        return null;
    }

    public ResponseEntity<?> restoreById(Long id) {
        if (appointmentRepository.findById(id).isPresent()) {
            Appointment fetchedAppointment = appointmentRepository.findById(id).get();
            if (fetchedAppointment.getSTATUS().equals(Status.ACTIVE)) {
                return ResponseEntity.badRequest().body("Already ACTIVE");
            } else {
                fetchedAppointment.setSTATUS(Status.ACTIVE);
                appointmentRepository.save(fetchedAppointment);
                return ResponseEntity.ok(fetchedAppointment);
            }
        }
        return null;
    }

    public List<Appointment> findAllByClientId(Long clientId) {
        return appointmentRepository.findAllByClientIdAndSTATUS(clientId, Status.ACTIVE);
    }

    public List<Appointment> findAllByUserId(Long userId) {
        return appointmentRepository.findAllByUserIdAndSTATUS(userId, Status.ACTIVE);
    }

}