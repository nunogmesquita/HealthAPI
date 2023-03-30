package HealthAPI.service;

import HealthAPI.dto.appointment.AppointmentCreateDto;
import HealthAPI.dto.appointment.AppointmentDto;
import HealthAPI.dto.appointment.AppointmentUpdateDto;

import java.util.List;

public interface AppointmentService {

    AppointmentDto createAppointment(String clientEmail, AppointmentCreateDto appointmentCreateDto);

    AppointmentDto findAppointmentById(Long appointmentId);

    void deleteAppointmentById(Long appointmentId);

    AppointmentDto updateAppointment(Long appointmentId, AppointmentUpdateDto appointmentUpdateDto);

    List<AppointmentDto> findAllByUserId(Long userId);

    List<AppointmentDto> findAllByClientId(Long clientId);

    AppointmentDto restoreById(Long appointmentId);

    List<AppointmentDto> findAllByClientEmail(String clientEmail);

}