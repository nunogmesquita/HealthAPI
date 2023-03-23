package HealthAPI.service;


import HealthAPI.dto.TimeSlotBookingRequest;
import HealthAPI.model.Appointment;
import HealthAPI.model.Status;
import HealthAPI.model.TimeSlot;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AppointmentService {

    public Appointment createAppointment(TimeSlotBookingRequest timeSlotRequest);

    public List<Appointment> findAppointmentByTimeSlot(TimeSlot currentTimeSlot, Long clientId);

    public List<Appointment> findBookedAppointment(TimeSlot currentTimeSlot);

    public boolean alreadyExist(TimeSlotBookingRequest timeSlotRequest, Long epochRequestedDate);

    public Appointment findById(Long id);

    public Appointment deleteById(Long id);

    public ResponseEntity<?> restoreById(Long id);

    public List<Appointment> findAllByClientId(Long clientId);

    public List<Appointment> findAllByHcpId(Long hcpId);

}
