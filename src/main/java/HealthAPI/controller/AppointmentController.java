package HealthAPI.controller;

import HealthAPI.dto.AppointmentCreateDto;
import HealthAPI.dto.AppointmentDto;
import HealthAPI.messages.Responses;
import HealthAPI.model.Appointment;
import HealthAPI.service.AppointmentService;
import HealthAPI.service.ClientService;
import HealthAPI.service.TimeSlotService;
import HealthAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private final UserService userService;
    private final TimeSlotService timeSlotService;
    private final ClientService clientService;
    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(UserService userService, TimeSlotService timeSlotService, ClientService clientService,
                                 AppointmentService appointmentService) {
        this.userService = userService;
        this.timeSlotService = timeSlotService;
        this.clientService = clientService;
        this.appointmentService = appointmentService;
    }

    @PostMapping("/create")
    public ResponseEntity<AppointmentDto> createAppointment(@RequestBody AppointmentCreateDto appointmentCreateDto) {
        AppointmentDto appointmentDto = appointmentService.createAppointment(appointmentCreateDto);
        return new ResponseEntity<>(appointmentDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDto> getAppointmentById(@PathVariable Long id) {
        AppointmentDto appointmentDto = appointmentService.findAppointmentById(id);
        return new ResponseEntity<>(appointmentDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointmentById(@PathVariable Long id) {
        appointmentService.deleteAppointmentById(id);
        return new ResponseEntity<>(Responses.DELETED_APPOINTMENT.formatted(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AppointmentDto> restoreById(@PathVariable Long id) {
        AppointmentDto appointmentDto = appointmentService.restoreById(id);
        return new ResponseEntity<>(appointmentDto, HttpStatus.OK);
    }

    @GetMapping("/getAllByClientId")
    public ResponseEntity<List<AppointmentDto>> getAllAppointmentByClientId(@RequestParam Long clientId) {
        List<AppointmentDto> appointmentList = appointmentService.findAllByClientId(clientId);
        return new ResponseEntity<>(appointmentList, HttpStatus.OK);
    }

    @GetMapping("/getAllByUserId")
    public ResponseEntity<List<AppointmentDto>> getAllAppointmentByUserId(@RequestParam Long userId) {
        List<AppointmentDto> appointmentList = appointmentService.findAllByUserId(userId);
        return new ResponseEntity<>(appointmentList, HttpStatus.OK);
    }

}