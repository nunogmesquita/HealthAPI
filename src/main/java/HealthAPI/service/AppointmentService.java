package HealthAPI.service;

import HealthAPI.converter.AppointmentConverter;
import HealthAPI.converter.ClientConverter;
import HealthAPI.dto.appointment.AppointmentCreateDto;
import HealthAPI.dto.appointment.AppointmentDto;
import HealthAPI.dto.client.ClientDto;
import HealthAPI.exception.AppointmentAlreadyActive;
import HealthAPI.exception.AppointmentNotFound;
import HealthAPI.model.*;
import HealthAPI.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserService userService;
    private final ClientService clientService;
    private final TimeSlotService timeSlotService;
    private final AppointmentConverter appointmentConverter;
    private final ClientConverter clientConverter;

    public AppointmentDto createAppointment(String clientEmail, AppointmentCreateDto appointmentCreateDto) {
        User user = userService.getUserById(appointmentCreateDto.getUserId());
        TimeSlot timeSlot = timeSlotService.getTimeSlotById(appointmentCreateDto.getTimeSlotId());
        ClientDto clientDto = clientService.getClientByEmail(clientEmail);
        Appointment appointment = Appointment.builder()
                .user(user)
                .timeSlot(timeSlot)
                .client(clientConverter.fromClientDtoToClient(clientDto))
                .status(Status.ACTIVE)
                .build();
        appointmentRepository.save(appointment);
        return appointmentConverter.fromAppointmentToAppointmentDto(appointment);
    }

    public AppointmentDto findAppointmentById(Long appointmentId) {
        Appointment appointment = appointmentRepository.findByIdAndStatus(appointmentId, Status.ACTIVE)
                .orElseThrow(AppointmentNotFound::new);
        return appointmentConverter.fromAppointmentToAppointmentDto(appointment);
    }

    public void deleteAppointmentById(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(AppointmentNotFound::new);
        appointment.setStatus(Status.INACTIVE);
        appointmentRepository.save(appointment);
    }

    public AppointmentDto updateAppointment(Long appointmentId, AppointmentCreateDto appointmentCreateDto) {
        Appointment appointmentToUpdate = appointmentConverter.fromAppointmentDtoAppointment(findAppointmentById(appointmentId));
        appointmentToUpdate.setUser(userService.getUserById(appointmentCreateDto.getUserId()));
        appointmentToUpdate.setTimeSlot(timeSlotService.getTimeSlotById(appointmentCreateDto.getTimeSlotId()));
        appointmentRepository.save(appointmentToUpdate);
        return appointmentConverter.fromAppointmentToAppointmentDto(appointmentToUpdate);
    }

    public List<AppointmentDto> findAllByUserId(Long userId) {
        List<Appointment> appointments = appointmentRepository.findAllByUser_IdAndStatus(userId, Status.ACTIVE);
        return appointments.parallelStream()
                .map(appointmentConverter::fromAppointmentToAppointmentDto)
                .toList();
    }

    public List<AppointmentDto> findAllByClientId(Long clientId) {
        List<Appointment> appointments = appointmentRepository.findAllByClient_IdAndStatus(clientId, Status.ACTIVE);
        return appointments.parallelStream()
                .map(appointmentConverter::fromAppointmentToAppointmentDto)
                .toList();
    }

    public AppointmentDto restoreById(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(AppointmentNotFound::new);
        if (appointment.getStatus().equals(Status.ACTIVE)) {
            throw new AppointmentAlreadyActive(appointmentId);
        } else {
            appointment.setStatus(Status.INACTIVE);
            appointmentRepository.save(appointment);
        }
        return appointmentConverter.fromAppointmentToAppointmentDto(appointment);
    }

}