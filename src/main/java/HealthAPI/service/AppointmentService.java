package HealthAPI.service;


import HealthAPI.dto.AppointmentDto;

import java.util.List;

public interface AppointmentService {
    AppointmentDto bookAppointment();
    AppointmentDto getAppointmentById(Long appointmentId);
    List<AppointmentDto> getAllAppointments();
    AppointmentDto updateAppointment(AppointmentDto appointmentDto);
    void deleteAppointment(Long appointmentId);

}
