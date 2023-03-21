package HealthAPI.service;


import HealthAPI.dto.AppointmentDto;
import HealthAPI.model.Appointment;
import jakarta.validation.Valid;

import java.util.List;

public interface AppointmentService {
    AppointmentDto bookAppointment(@Valid AppointmentDto appointment);
    AppointmentDto getAppointmentById(Long appointmentId);
    List<AppointmentDto> getAllAppointments();
    AppointmentDto updateAppointment(AppointmentDto appointmentDto);
    void deleteAppointment(Long appointmentId);

}
