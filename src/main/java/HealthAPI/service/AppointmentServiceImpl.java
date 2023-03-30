package HealthAPI.service;

import HealthAPI.converter.AppointmentConverter;
import HealthAPI.converter.ClientConverter;
import HealthAPI.converter.UserConverter;
import HealthAPI.dto.appointment.AppointmentCreateDto;
import HealthAPI.dto.appointment.AppointmentDto;
import HealthAPI.dto.appointment.AppointmentUpdateDto;
import HealthAPI.dto.client.ClientDto;
import HealthAPI.exception.AppointmentAlreadyActive;
import HealthAPI.exception.AppointmentNotFound;
import HealthAPI.exception.TimeSlotAlreadyBooked;
import HealthAPI.model.*;
import HealthAPI.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserServiceImpl userService;
    private final ClientServiceImpl clientService;
    private final TimeSlotServiceImpl timeSlotService;
    private final AppointmentConverter appointmentConverter;
    private final ClientConverter clientConverter;
    private final UserConverter userConverter;

    public AppointmentDto createAppointment(String clientEmail, AppointmentCreateDto appointmentCreateDto) {
        TimeSlot timeSlot = timeSlotService.getTimeSlotById(appointmentCreateDto.getTimeSlotId());
        if (timeSlot.isBooked()) {
            throw new TimeSlotAlreadyBooked(timeSlot.getId());
        }
        ClientDto clientDto = clientService.getClientByEmail(clientEmail);
        Appointment appointment = Appointment.builder()
                .user(timeSlot.getUser())
                .timeSlot(timeSlot)
                .client(clientConverter.fromClientDtoToClient(clientDto))
                .status(Status.ACTIVE)
                .build();
        timeSlot.setAppointment(appointment);
        timeSlot.setBooked(true);
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

    public AppointmentDto updateAppointment(Long appointmentId, AppointmentUpdateDto appointmentUpdateDto) {
        Appointment appointmentToUpdate = appointmentConverter.fromAppointmentDtoAppointment(findAppointmentById(appointmentId));
        NullUtils.updateIfPresent(appointmentToUpdate::setUser, userConverter.fromUserDtoToUser(userService.getUserById(appointmentUpdateDto.getUserId())));
        NullUtils.updateIfPresent(appointmentToUpdate::setTimeSlot, timeSlotService.getTimeSlotById(appointmentUpdateDto.getTimeSlotId()));
        appointmentRepository.save(appointmentToUpdate);
        return appointmentConverter.fromAppointmentToAppointmentDto(appointmentToUpdate);
    }

    public List<AppointmentDto> findAllByUserId(Long userId) {
        userService.getUserById(userId);
        List<Appointment> appointments = appointmentRepository.findAllByUser_IdAndStatus(userId, Status.ACTIVE);
        return appointments.parallelStream()
                .map(appointmentConverter::fromAppointmentToAppointmentDto)
                .toList();
    }

    public List<AppointmentDto> findAllByClientId(Long clientId) {
        clientService.getClientById(clientId);
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
            appointment.setStatus(Status.ACTIVE);
            appointmentRepository.save(appointment);
        }
        return appointmentConverter.fromAppointmentToAppointmentDto(appointment);
    }

    public List<AppointmentDto> findAllByClientEmail(String clientEmail) {
        List<Appointment> appointments = appointmentRepository.findAllByClient_EmailAndStatus(clientEmail, Status.ACTIVE);
        return appointments.parallelStream()
                .map(appointmentConverter::fromAppointmentToAppointmentDto)
                .toList();
    }

}