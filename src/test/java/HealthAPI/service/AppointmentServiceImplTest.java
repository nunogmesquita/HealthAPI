package HealthAPI.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import HealthAPI.converter.AppointmentConverter;
import HealthAPI.converter.ClientConverter;
import HealthAPI.converter.UserConverter;
import HealthAPI.dto.appointment.AppointmentCreateDto;
import HealthAPI.dto.appointment.AppointmentDto;
import HealthAPI.dto.appointment.AppointmentUpdateDto;
import HealthAPI.dto.client.ClientDto;
import HealthAPI.dto.user.UserDto;
import HealthAPI.exception.AppointmentAlreadyActive;
import HealthAPI.exception.AppointmentNotFound;
import HealthAPI.exception.TimeSlotAlreadyBooked;
import HealthAPI.model.Appointment;
import HealthAPI.model.Client;
import HealthAPI.model.Status;
import HealthAPI.model.TimeSlot;
import HealthAPI.model.User;
import HealthAPI.repository.AppointmentRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AppointmentServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AppointmentServiceImplTest {
    @MockBean
    private AppointmentConverter appointmentConverter;

    @MockBean
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentServiceImpl appointmentServiceImpl;

    @MockBean
    private ClientConverter clientConverter;

    @MockBean
    private ClientServiceImpl clientServiceImpl;

    @MockBean
    private TimeSlotServiceImpl timeSlotServiceImpl;

    @MockBean
    private UserConverter userConverter;

    @MockBean
    private UserServiceImpl userServiceImpl;

    @Test
    void testCreateAppointment() {
        when(appointmentRepository.save((Appointment) any())).thenReturn(new Appointment());
        when(clientServiceImpl.getClientByEmail((String) any())).thenReturn(new ClientDto());
        when(timeSlotServiceImpl.getTimeSlotById((Long) any())).thenReturn(new TimeSlot());
        AppointmentDto appointmentDto = new AppointmentDto();
        when(appointmentConverter.fromAppointmentToAppointmentDto((Appointment) any())).thenReturn(appointmentDto);
        when(clientConverter.fromClientDtoToClient((ClientDto) any())).thenReturn(new Client());
        assertSame(appointmentDto,
                appointmentServiceImpl.createAppointment("jane.doe@example.org", new AppointmentCreateDto()));
        verify(appointmentRepository).save((Appointment) any());
        verify(clientServiceImpl).getClientByEmail((String) any());
        verify(timeSlotServiceImpl).getTimeSlotById((Long) any());
        verify(appointmentConverter).fromAppointmentToAppointmentDto((Appointment) any());
        verify(clientConverter).fromClientDtoToClient((ClientDto) any());
    }

    @Test
    void testCreateAppointment2() {
        when(appointmentRepository.save((Appointment) any())).thenReturn(new Appointment());
        when(clientServiceImpl.getClientByEmail((String) any())).thenReturn(new ClientDto());
        when(timeSlotServiceImpl.getTimeSlotById((Long) any())).thenReturn(new TimeSlot());
        when(appointmentConverter.fromAppointmentToAppointmentDto((Appointment) any())).thenReturn(new AppointmentDto());
        when(clientConverter.fromClientDtoToClient((ClientDto) any())).thenThrow(new TimeSlotAlreadyBooked(1L));
        assertThrows(TimeSlotAlreadyBooked.class,
                () -> appointmentServiceImpl.createAppointment("jane.doe@example.org", new AppointmentCreateDto()));
        verify(clientServiceImpl).getClientByEmail((String) any());
        verify(timeSlotServiceImpl).getTimeSlotById((Long) any());
        verify(clientConverter).fromClientDtoToClient((ClientDto) any());
    }

    @Test
    void testCreateAppointment3() {
        when(appointmentRepository.save((Appointment) any())).thenReturn(new Appointment());
        when(clientServiceImpl.getClientByEmail((String) any())).thenReturn(new ClientDto());
        LocalDateTime startTime = LocalDateTime.of(1, 1, 1, 1, 1);
        User user = new User();
        when(timeSlotServiceImpl.getTimeSlotById((Long) any()))
                .thenReturn(new TimeSlot(1L, startTime, "Day Of Week", user, new Appointment(), true));
        when(appointmentConverter.fromAppointmentToAppointmentDto((Appointment) any())).thenReturn(new AppointmentDto());
        when(clientConverter.fromClientDtoToClient((ClientDto) any())).thenReturn(new Client());
        assertThrows(TimeSlotAlreadyBooked.class,
                () -> appointmentServiceImpl.createAppointment("jane.doe@example.org", new AppointmentCreateDto()));
        verify(timeSlotServiceImpl).getTimeSlotById((Long) any());
    }

    @Test
    void testCreateAppointment5() {
        when(appointmentRepository.save((Appointment) any())).thenReturn(new Appointment());
        when(clientServiceImpl.getClientByEmail((String) any())).thenReturn(new ClientDto());
        TimeSlot timeSlot = mock(TimeSlot.class);
        when(timeSlot.getId()).thenReturn(1L);
        when(timeSlot.getUser()).thenReturn(new User());
        when(timeSlot.isBooked()).thenReturn(true);
        doNothing().when(timeSlot).setAppointment((Appointment) any());
        doNothing().when(timeSlot).setBooked(anyBoolean());
        when(timeSlotServiceImpl.getTimeSlotById((Long) any())).thenReturn(timeSlot);
        when(appointmentConverter.fromAppointmentToAppointmentDto((Appointment) any())).thenReturn(new AppointmentDto());
        when(clientConverter.fromClientDtoToClient((ClientDto) any())).thenReturn(new Client());
        assertThrows(TimeSlotAlreadyBooked.class,
                () -> appointmentServiceImpl.createAppointment("jane.doe@example.org", new AppointmentCreateDto()));
        verify(timeSlotServiceImpl).getTimeSlotById((Long) any());
        verify(timeSlot).isBooked();
        verify(timeSlot).getId();
    }

    @Test
    void testCreateAppointment6() {
        when(appointmentRepository.save((Appointment) any())).thenReturn(new Appointment());
        when(clientServiceImpl.getClientByEmail((String) any())).thenReturn(new ClientDto());
        TimeSlot timeSlot = mock(TimeSlot.class);
        when(timeSlot.getId()).thenThrow(new TimeSlotAlreadyBooked(1L));
        when(timeSlot.getUser()).thenReturn(new User());
        when(timeSlot.isBooked()).thenReturn(true);
        doNothing().when(timeSlot).setAppointment((Appointment) any());
        doNothing().when(timeSlot).setBooked(anyBoolean());
        when(timeSlotServiceImpl.getTimeSlotById((Long) any())).thenReturn(timeSlot);
        when(appointmentConverter.fromAppointmentToAppointmentDto((Appointment) any())).thenReturn(new AppointmentDto());
        when(clientConverter.fromClientDtoToClient((ClientDto) any())).thenReturn(new Client());
        assertThrows(TimeSlotAlreadyBooked.class,
                () -> appointmentServiceImpl.createAppointment("jane.doe@example.org", new AppointmentCreateDto()));
        verify(timeSlotServiceImpl).getTimeSlotById((Long) any());
        verify(timeSlot).isBooked();
        verify(timeSlot).getId();
    }

    @Test
    void testFindAppointmentById() {
        when(appointmentRepository.findByIdAndStatus((Long) any(), (Status) any()))
                .thenReturn(Optional.of(new Appointment()));
        AppointmentDto appointmentDto = new AppointmentDto();
        when(appointmentConverter.fromAppointmentToAppointmentDto((Appointment) any())).thenReturn(appointmentDto);
        assertSame(appointmentDto, appointmentServiceImpl.findAppointmentById(1L));
        verify(appointmentRepository).findByIdAndStatus((Long) any(), (Status) any());
        verify(appointmentConverter).fromAppointmentToAppointmentDto((Appointment) any());
    }

    @Test
    void testFindAppointmentById2() {
        when(appointmentRepository.findByIdAndStatus((Long) any(), (Status) any()))
                .thenReturn(Optional.of(new Appointment()));
        when(appointmentConverter.fromAppointmentToAppointmentDto((Appointment) any()))
                .thenThrow(new TimeSlotAlreadyBooked(1L));
        assertThrows(TimeSlotAlreadyBooked.class, () -> appointmentServiceImpl.findAppointmentById(1L));
        verify(appointmentRepository).findByIdAndStatus((Long) any(), (Status) any());
        verify(appointmentConverter).fromAppointmentToAppointmentDto((Appointment) any());
    }

    @Test
    void testDeleteAppointmentById() {
        when(appointmentRepository.save((Appointment) any())).thenReturn(new Appointment());
        when(appointmentRepository.findById((Long) any())).thenReturn(Optional.of(new Appointment()));
        appointmentServiceImpl.deleteAppointmentById(1L);
        verify(appointmentRepository).save((Appointment) any());
        verify(appointmentRepository).findById((Long) any());
    }

    @Test
    void testDeleteAppointmentById2() {
        when(appointmentRepository.save((Appointment) any())).thenThrow(new TimeSlotAlreadyBooked(1L));
        when(appointmentRepository.findById((Long) any())).thenReturn(Optional.of(new Appointment()));
        assertThrows(TimeSlotAlreadyBooked.class, () -> appointmentServiceImpl.deleteAppointmentById(1L));
        verify(appointmentRepository).save((Appointment) any());
        verify(appointmentRepository).findById((Long) any());
    }


    @Test
    void testDeleteAppointmentById3() {
        Appointment appointment = mock(Appointment.class);
        doNothing().when(appointment).setStatus((Status) any());
        Optional<Appointment> ofResult = Optional.of(appointment);
        when(appointmentRepository.save((Appointment) any())).thenReturn(new Appointment());
        when(appointmentRepository.findById((Long) any())).thenReturn(ofResult);
        appointmentServiceImpl.deleteAppointmentById(1L);
        verify(appointmentRepository).save((Appointment) any());
        verify(appointmentRepository).findById((Long) any());
        verify(appointment).setStatus((Status) any());
    }

    @Test
    void testDeleteAppointmentById5() {
        Appointment appointment = mock(Appointment.class);
        doThrow(new TimeSlotAlreadyBooked(1L)).when(appointment).setStatus((Status) any());
        Optional<Appointment> ofResult = Optional.of(appointment);
        when(appointmentRepository.save((Appointment) any())).thenReturn(new Appointment());
        when(appointmentRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(TimeSlotAlreadyBooked.class, () -> appointmentServiceImpl.deleteAppointmentById(1L));
        verify(appointmentRepository).findById((Long) any());
        verify(appointment).setStatus((Status) any());
    }

    @Test
    void testUpdateAppointment() {
        when(appointmentRepository.save((Appointment) any())).thenReturn(new Appointment());
        when(appointmentRepository.findByIdAndStatus((Long) any(), (Status) any()))
                .thenReturn(Optional.of(new Appointment()));
        when(userServiceImpl.getUserById((Long) any())).thenReturn(new UserDto());
        when(timeSlotServiceImpl.getTimeSlotById((Long) any())).thenReturn(new TimeSlot());
        AppointmentDto appointmentDto = new AppointmentDto();
        when(appointmentConverter.fromAppointmentToAppointmentDto((Appointment) any())).thenReturn(appointmentDto);
        when(appointmentConverter.fromAppointmentDtoAppointment((AppointmentDto) any())).thenReturn(new Appointment());
        when(userConverter.fromUserDtoToUser((UserDto) any())).thenReturn(new User());
        assertSame(appointmentDto, appointmentServiceImpl.updateAppointment(1L, new AppointmentUpdateDto()));
        verify(appointmentRepository).save((Appointment) any());
        verify(appointmentRepository).findByIdAndStatus((Long) any(), (Status) any());
        verify(userServiceImpl).getUserById((Long) any());
        verify(timeSlotServiceImpl).getTimeSlotById((Long) any());
        verify(appointmentConverter, atLeast(1)).fromAppointmentToAppointmentDto((Appointment) any());
        verify(appointmentConverter).fromAppointmentDtoAppointment((AppointmentDto) any());
        verify(userConverter).fromUserDtoToUser((UserDto) any());
    }

    @Test
    void testUpdateAppointment2() {
        when(appointmentRepository.save((Appointment) any())).thenReturn(new Appointment());
        when(appointmentRepository.findByIdAndStatus((Long) any(), (Status) any()))
                .thenReturn(Optional.of(new Appointment()));
        when(userServiceImpl.getUserById((Long) any())).thenReturn(new UserDto());
        when(timeSlotServiceImpl.getTimeSlotById((Long) any())).thenReturn(new TimeSlot());
        when(appointmentConverter.fromAppointmentToAppointmentDto((Appointment) any())).thenReturn(new AppointmentDto());
        when(appointmentConverter.fromAppointmentDtoAppointment((AppointmentDto) any())).thenReturn(new Appointment());
        when(userConverter.fromUserDtoToUser((UserDto) any())).thenThrow(new TimeSlotAlreadyBooked(1L));
        assertThrows(TimeSlotAlreadyBooked.class,
                () -> appointmentServiceImpl.updateAppointment(1L, new AppointmentUpdateDto()));
        verify(appointmentRepository).findByIdAndStatus((Long) any(), (Status) any());
        verify(userServiceImpl).getUserById((Long) any());
        verify(appointmentConverter).fromAppointmentToAppointmentDto((Appointment) any());
        verify(appointmentConverter).fromAppointmentDtoAppointment((AppointmentDto) any());
        verify(userConverter).fromUserDtoToUser((UserDto) any());
    }

    @Test
    void testUpdateAppointment4() {
        when(appointmentRepository.save((Appointment) any())).thenReturn(new Appointment());
        when(appointmentRepository.findByIdAndStatus((Long) any(), (Status) any()))
                .thenReturn(Optional.of(new Appointment()));
        when(userServiceImpl.getUserById((Long) any())).thenReturn(new UserDto());
        when(timeSlotServiceImpl.getTimeSlotById((Long) any())).thenReturn(null);
        AppointmentDto appointmentDto = new AppointmentDto();
        when(appointmentConverter.fromAppointmentToAppointmentDto((Appointment) any())).thenReturn(appointmentDto);
        when(appointmentConverter.fromAppointmentDtoAppointment((AppointmentDto) any())).thenReturn(new Appointment());
        when(userConverter.fromUserDtoToUser((UserDto) any())).thenReturn(new User());
        assertSame(appointmentDto, appointmentServiceImpl.updateAppointment(1L, new AppointmentUpdateDto()));
        verify(appointmentRepository).save((Appointment) any());
        verify(appointmentRepository).findByIdAndStatus((Long) any(), (Status) any());
        verify(userServiceImpl).getUserById((Long) any());
        verify(timeSlotServiceImpl).getTimeSlotById((Long) any());
        verify(appointmentConverter, atLeast(1)).fromAppointmentToAppointmentDto((Appointment) any());
        verify(appointmentConverter).fromAppointmentDtoAppointment((AppointmentDto) any());
        verify(userConverter).fromUserDtoToUser((UserDto) any());
    }

    @Test
    void testUpdateAppointment6() {
        when(appointmentRepository.save((Appointment) any())).thenReturn(new Appointment());
        when(appointmentRepository.findByIdAndStatus((Long) any(), (Status) any()))
                .thenReturn(Optional.of(new Appointment()));
        when(userServiceImpl.getUserById((Long) any())).thenReturn(new UserDto());
        when(timeSlotServiceImpl.getTimeSlotById((Long) any())).thenReturn(new TimeSlot());
        Appointment appointment = mock(Appointment.class);
        doNothing().when(appointment).setTimeSlot((TimeSlot) any());
        doNothing().when(appointment).setUser((User) any());
        AppointmentDto appointmentDto = new AppointmentDto();
        when(appointmentConverter.fromAppointmentToAppointmentDto((Appointment) any())).thenReturn(appointmentDto);
        when(appointmentConverter.fromAppointmentDtoAppointment((AppointmentDto) any())).thenReturn(appointment);
        when(userConverter.fromUserDtoToUser((UserDto) any())).thenReturn(new User());
        assertSame(appointmentDto, appointmentServiceImpl.updateAppointment(1L, new AppointmentUpdateDto()));
        verify(appointmentRepository).save((Appointment) any());
        verify(appointmentRepository).findByIdAndStatus((Long) any(), (Status) any());
        verify(userServiceImpl).getUserById((Long) any());
        verify(timeSlotServiceImpl).getTimeSlotById((Long) any());
        verify(appointmentConverter, atLeast(1)).fromAppointmentToAppointmentDto((Appointment) any());
        verify(appointmentConverter).fromAppointmentDtoAppointment((AppointmentDto) any());
        verify(appointment).setTimeSlot((TimeSlot) any());
        verify(appointment).setUser((User) any());
        verify(userConverter).fromUserDtoToUser((UserDto) any());
    }

    @Test
    void testFindAllByUserId() {
        when(appointmentRepository.findAllByUser_IdAndStatus((Long) any(), (Status) any())).thenReturn(new ArrayList<>());
        when(userServiceImpl.getUserById((Long) any())).thenReturn(new UserDto());
        assertTrue(appointmentServiceImpl.findAllByUserId(1L).isEmpty());
        verify(appointmentRepository).findAllByUser_IdAndStatus((Long) any(), (Status) any());
        verify(userServiceImpl).getUserById((Long) any());
    }

    @Test
    void testFindAllByUserId2() {
        when(appointmentRepository.findAllByUser_IdAndStatus((Long) any(), (Status) any())).thenReturn(new ArrayList<>());
        when(userServiceImpl.getUserById((Long) any())).thenThrow(new TimeSlotAlreadyBooked(1L));
        assertThrows(TimeSlotAlreadyBooked.class, () -> appointmentServiceImpl.findAllByUserId(1L));
        verify(userServiceImpl).getUserById((Long) any());
    }

    @Test
    void testFindAllByClientId() {
        when(appointmentRepository.findAllByClient_IdAndStatus((Long) any(), (Status) any()))
                .thenReturn(new ArrayList<>());
        when(clientServiceImpl.getClientById((Long) any())).thenReturn(new ClientDto());
        assertTrue(appointmentServiceImpl.findAllByClientId(1L).isEmpty());
        verify(appointmentRepository).findAllByClient_IdAndStatus((Long) any(), (Status) any());
        verify(clientServiceImpl).getClientById((Long) any());
    }

    @Test
    void testFindAllByClientId2() {
        when(appointmentRepository.findAllByClient_IdAndStatus((Long) any(), (Status) any()))
                .thenReturn(new ArrayList<>());
        when(clientServiceImpl.getClientById((Long) any())).thenThrow(new TimeSlotAlreadyBooked(1L));
        assertThrows(TimeSlotAlreadyBooked.class, () -> appointmentServiceImpl.findAllByClientId(1L));
        verify(clientServiceImpl).getClientById((Long) any());
    }

    @Test
    void testRestoreById2() {
        User user = new User();
        TimeSlot timeSlot = new TimeSlot();
        when(appointmentRepository.findById((Long) any()))
                .thenReturn(Optional.of(new Appointment(1L, user, timeSlot, new Client(), Status.ACTIVE)));
        assertThrows(AppointmentAlreadyActive.class, () -> appointmentServiceImpl.restoreById(1L));
        verify(appointmentRepository).findById((Long) any());
    }

    @Test
    void testRestoreById3() {
        Appointment appointment = mock(Appointment.class);
        when(appointment.getStatus()).thenReturn(Status.ACTIVE);
        Optional<Appointment> ofResult = Optional.of(appointment);
        when(appointmentRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(AppointmentAlreadyActive.class, () -> appointmentServiceImpl.restoreById(1L));
        verify(appointmentRepository).findById((Long) any());
        verify(appointment).getStatus();
    }

    @Test
    void testRestoreById4() {
        Appointment appointment = mock(Appointment.class);
        doNothing().when(appointment).setStatus((Status) any());
        when(appointment.getStatus()).thenReturn(Status.INACTIVE);
        Optional<Appointment> ofResult = Optional.of(appointment);
        when(appointmentRepository.save((Appointment) any())).thenReturn(new Appointment());
        when(appointmentRepository.findById((Long) any())).thenReturn(ofResult);
        AppointmentDto appointmentDto = new AppointmentDto();
        when(appointmentConverter.fromAppointmentToAppointmentDto((Appointment) any())).thenReturn(appointmentDto);
        assertSame(appointmentDto, appointmentServiceImpl.restoreById(1L));
        verify(appointmentRepository).save((Appointment) any());
        verify(appointmentRepository).findById((Long) any());
        verify(appointment).getStatus();
        verify(appointment).setStatus((Status) any());
        verify(appointmentConverter).fromAppointmentToAppointmentDto((Appointment) any());
    }

    @Test
    void testRestoreById5() {
        Appointment appointment = mock(Appointment.class);
        doNothing().when(appointment).setStatus((Status) any());
        when(appointment.getStatus()).thenReturn(Status.INACTIVE);
        Optional<Appointment> ofResult = Optional.of(appointment);
        when(appointmentRepository.save((Appointment) any())).thenReturn(new Appointment());
        when(appointmentRepository.findById((Long) any())).thenReturn(ofResult);
        when(appointmentConverter.fromAppointmentToAppointmentDto((Appointment) any()))
                .thenThrow(new TimeSlotAlreadyBooked(1L));
        assertThrows(TimeSlotAlreadyBooked.class, () -> appointmentServiceImpl.restoreById(1L));
        verify(appointmentRepository).save((Appointment) any());
        verify(appointmentRepository).findById((Long) any());
        verify(appointment).getStatus();
        verify(appointment).setStatus((Status) any());
        verify(appointmentConverter).fromAppointmentToAppointmentDto((Appointment) any());
    }

    @Test
    void testFindAllByClientEmail() {
        when(appointmentRepository.findAllByClient_EmailAndStatus((String) any(), (Status) any()))
                .thenReturn(new ArrayList<>());
        assertTrue(appointmentServiceImpl.findAllByClientEmail("jane.doe@example.org").isEmpty());
        verify(appointmentRepository).findAllByClient_EmailAndStatus((String) any(), (Status) any());
    }
}