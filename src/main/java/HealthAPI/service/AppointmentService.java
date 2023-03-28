package HealthAPI.service;

import HealthAPI.converter.AppointmentConverter;
import HealthAPI.converter.ClientConverter;
import HealthAPI.dto.AppointmentCreateDto;
import HealthAPI.dto.AppointmentDto;
import HealthAPI.dto.Client.ClientDto;
import HealthAPI.model.*;
import HealthAPI.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserService userService;
    private final ClientService clientService;
    private final TimeSlotService timeSlotService;
    private final AppointmentConverter appointmentConverter;
    private final ClientConverter clientConverter;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, UserService userService,
                              ClientService clientService, TimeSlotService timeSlotService,
                              AppointmentConverter appointmentConverter, ClientConverter clientConverter) {
        this.appointmentRepository = appointmentRepository;
        this.userService = userService;
        this.clientService = clientService;
        this.timeSlotService = timeSlotService;
        this.appointmentConverter = appointmentConverter;
        this.clientConverter = clientConverter;
    }

    public AppointmentDto createAppointment(AppointmentCreateDto appointmentCreateDto) {
        User user = userService.getUserById(appointmentCreateDto.getUserId());
        TimeSlot timeSlot = timeSlotService.getTimeSlotById(appointmentCreateDto.getTimeSlotId());
        ClientDto client = clientService.getClientById(appointmentCreateDto.getClientId());
        Appointment appointment = Appointment.builder()
                .user(user)
                .timeSlot(timeSlot)
                .client(clientConverter.fromClientDtoToClient(client))
                .build();
        appointmentRepository.save(appointment);
        return appointmentConverter.fromAppointmentToAppointmentDto(appointment);
    }

    public AppointmentDto findAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow();
        return appointmentConverter.fromAppointmentToAppointmentDto(appointment);
    }

    public void deleteAppointmentById(Long id) {
        Appointment fetchedAppointment = appointmentConverter.fromAppointmentDtoAppointment(findAppointmentById(id));
        if (fetchedAppointment != null) {
            fetchedAppointment.setSTATUS(Status.INACTIVE);
            appointmentRepository.save(fetchedAppointment);
        }
    }

    public AppointmentDto restoreById(Long id) {
        if (appointmentRepository.findById(id).isPresent()) {
            Appointment fetchedAppointment = appointmentRepository.findById(id).get();
            return appointmentConverter.fromAppointmentToAppointmentDto(fetchedAppointment);
        }

//            if (fetchedAppointment.getSTATUS().equals(Status.ACTIVE)) {
//                return ResponseEntity.badRequest().body("Already ACTIVE");
//            } else {
//                fetchedAppointment.setSTATUS(Status.ACTIVE);
//                appointmentRepository.save(fetchedAppointment);
//                return ResponseEntity.ok(fetchedAppointment);
        return null;
    }

    public List<AppointmentDto> findAllByClientId(Long clientId) {
        List<Appointment> appointments = appointmentRepository.findAllByClientIdAndSTATUS(clientId, Status.ACTIVE);
        return appointments.parallelStream()
                .map(appointmentConverter::fromAppointmentToAppointmentDto)
                .toList();
    }

    public List<AppointmentDto> findAllByUserId(Long userId) {
        List<Appointment> appointments = appointmentRepository.findAllByUserIdAndSTATUS(userId, Status.ACTIVE);
        return appointments.parallelStream()
                .map(appointmentConverter::fromAppointmentToAppointmentDto)
                .toList();
    }

}