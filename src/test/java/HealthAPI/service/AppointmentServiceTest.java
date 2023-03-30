package HealthAPI.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import HealthAPI.converter.AppointmentConverter;
import HealthAPI.converter.ClientConverter;
import HealthAPI.dto.appointment.AppointmentCreateDto;
import HealthAPI.dto.appointment.AppointmentDto;
import HealthAPI.dto.appointment.AppointmentUpdateDto;
import HealthAPI.dto.client.ClientDto;
import HealthAPI.dto.user.UserDto;
import HealthAPI.exception.AppointmentAlreadyActive;
import HealthAPI.model.Appointment;
import HealthAPI.model.Client;
import HealthAPI.model.Status;
import HealthAPI.model.TimeSlot;
import HealthAPI.model.User;
import HealthAPI.repository.AppointmentRepository;

import java.util.ArrayList;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AppointmentService.class})
@ExtendWith(SpringExtension.class)
class AppointmentServiceTest {
    @MockBean
    private AppointmentConverter appointmentConverter;

    @MockBean
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentService appointmentService;

    @MockBean
    private ClientConverter clientConverter;

    @MockBean
    private ClientService clientService;

    @MockBean
    private TimeSlotService timeSlotService;

    @MockBean
    private UserService userService;

    @Test
    void testCreateAppointment() {
        when(appointmentRepository.save((Appointment) any())).thenReturn(new Appointment());
        when(userService.getUserById((Long) any())).thenReturn(new UserDto());
        when(clientService.getClientByEmail((String) any())).thenReturn(new ClientDto());
        when(timeSlotService.getTimeSlotById((Long) any())).thenReturn(new TimeSlot());
        AppointmentDto appointmentDto = new AppointmentDto();
        when(appointmentConverter.fromAppointmentToAppointmentDto((Appointment) any())).thenReturn(appointmentDto);
        when(clientConverter.fromClientDtoToClient((ClientDto) any())).thenReturn(new Client());
        assertSame(appointmentDto,
                appointmentService.createAppointment("jane.doe@example.org", new AppointmentCreateDto()));
        verify(appointmentRepository).save((Appointment) any());
        verify(userService).getUserById((Long) any());
        verify(clientService).getClientByEmail((String) any());
        verify(timeSlotService).getTimeSlotById((Long) any());
        verify(appointmentConverter).fromAppointmentToAppointmentDto((Appointment) any());
        verify(clientConverter).fromClientDtoToClient((ClientDto) any());
    }
    @Test
    void testFindAppointmentById() {
        when(appointmentRepository.findByIdAndStatus((Long) any(), (Status) any()))
                .thenReturn(Optional.of(new Appointment()));
        AppointmentDto appointmentDto = new AppointmentDto();
        when(appointmentConverter.fromAppointmentToAppointmentDto((Appointment) any())).thenReturn(appointmentDto);
        assertSame(appointmentDto, appointmentService.findAppointmentById(1L));
        verify(appointmentRepository).findByIdAndStatus((Long) any(), (Status) any());
        verify(appointmentConverter).fromAppointmentToAppointmentDto((Appointment) any());
    }
    @Test
    void testDeleteAppointmentById() {
        when(appointmentRepository.save((Appointment) any())).thenReturn(new Appointment());
        when(appointmentRepository.findById((Long) any())).thenReturn(Optional.of(new Appointment()));
        appointmentService.deleteAppointmentById(1L);
        verify(appointmentRepository).save((Appointment) any());
        verify(appointmentRepository).findById((Long) any());
    }
    @Test
    void testUpdateAppointment() {
        when(appointmentRepository.save((Appointment) any())).thenReturn(new Appointment());
        when(appointmentRepository.findByIdAndStatus((Long) any(), (Status) any()))
                .thenReturn(Optional.of(new Appointment()));
        when(userService.getUserById((Long) any())).thenReturn(new UserDto());
        when(timeSlotService.getTimeSlotById((Long) any())).thenReturn(new TimeSlot());
        AppointmentDto appointmentDto = new AppointmentDto();
        when(appointmentConverter.fromAppointmentToAppointmentDto((Appointment) any())).thenReturn(appointmentDto);
        when(appointmentConverter.fromAppointmentDtoAppointment((AppointmentDto) any())).thenReturn(new Appointment());
        assertSame(appointmentDto, appointmentService.updateAppointment(1L, new AppointmentUpdateDto()));
        verify(appointmentRepository).save((Appointment) any());
        verify(appointmentRepository).findByIdAndStatus((Long) any(), (Status) any());
        verify(userService).getUserById((Long) any());
        verify(timeSlotService).getTimeSlotById((Long) any());
        verify(appointmentConverter, atLeast(1)).fromAppointmentToAppointmentDto((Appointment) any());
        verify(appointmentConverter).fromAppointmentDtoAppointment((AppointmentDto) any());
    }

    @Test
    void testFindAllByUserId() {
        when(appointmentRepository.findAllByUser_IdAndStatus((Long) any(), (Status) any())).thenReturn(new ArrayList<>());
        assertTrue(appointmentService.findAllByUserId(1L).isEmpty());
        verify(appointmentRepository).findAllByUser_IdAndStatus((Long) any(), (Status) any());
    }

    @Test
    void testFindAllByClientId() {
        when(appointmentRepository.findAllByClient_IdAndStatus((Long) any(), (Status) any()))
                .thenReturn(new ArrayList<>());
        assertTrue(appointmentService.findAllByClientId(1L).isEmpty());
        verify(appointmentRepository).findAllByClient_IdAndStatus((Long) any(), (Status) any());
    }

    @Test
    void testRestoreById() {
        User user = new User();
        TimeSlot timeSlot = new TimeSlot();
        when(appointmentRepository.findById((Long) any()))
                .thenReturn(Optional.of(new Appointment(1L, user, timeSlot, new Client(), Status.ACTIVE)));
        assertThrows(AppointmentAlreadyActive.class, () -> appointmentService.restoreById(1L));
        verify(appointmentRepository).findById((Long) any());
    }
}

