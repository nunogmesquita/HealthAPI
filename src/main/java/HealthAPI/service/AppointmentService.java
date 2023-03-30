package HealthAPI.service;

import HealthAPI.converter.AppointmentConverter;
import HealthAPI.converter.ClientConverter;
import HealthAPI.converter.UserConverter;
import HealthAPI.dto.appointment.AppointmentCreateDto;
import HealthAPI.dto.appointment.AppointmentDto;
import HealthAPI.dto.appointment.AppointmentUpdateDto;
import HealthAPI.dto.client.ClientDto;
import HealthAPI.dto.user.UserDto;
import HealthAPI.exception.AppointmentAlreadyActive;
import HealthAPI.exception.AppointmentAlreadyInactive;
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
    private final UserConverter userConverter;

    public AppointmentDto createAppointment(String clientEmail, AppointmentCreateDto appointmentCreateDto) {
        UserDto userDto = userService.getUserById(appointmentCreateDto.getUserId());
        User user = userConverter.fromUserDtoToUser(userDto);
        TimeSlot timeSlot = timeSlotService.getTimeSlotById(appointmentCreateDto.getTimeSlotId());
        ClientDto clientDto = clientService.getClientByEmail(clientEmail);
        Appointment appointment = Appointment.builder()
                .user(user)
                .timeSlot(timeSlot)
                .client(clientConverter.fromClientDtoToClient(clientDto))
                .status(Status.ACTIVE)
                .build();
        timeSlot.setAppointment(appointment);
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
            appointment.setStatus(Status.ACTIVE);
            appointmentRepository.save(appointment);
        }
        return appointmentConverter.fromAppointmentToAppointmentDto(appointment);
    }

}