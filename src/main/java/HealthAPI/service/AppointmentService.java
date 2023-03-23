package HealthAPI.service;


import HealthAPI.dto.AppointmentDto;
import HealthAPI.model.Appointment;

import java.util.List;

public interface AppointmentService {
    AppointmentDto bookAppointment(Appointment appointment);
    AppointmentDto getAppointmentById(Long appointmentId);
    List<AppointmentDto> getAllAppointments();
    AppointmentDto updateAppointment(Long id, AppointmentDto appointmentDto);
    List<AppointmentDto> getAppointmentType();
    void deleteAppointment(Long appointmentId);

}
