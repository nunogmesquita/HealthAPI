package HealthAPI.controller;

import HealthAPI.converter.UserConverter;
import HealthAPI.dto.AppointmentResponse;
import HealthAPI.dto.BaseResponse;
import HealthAPI.model.Appointment;
import HealthAPI.model.Client;
import HealthAPI.model.TimeSlot;
import HealthAPI.model.User;
import HealthAPI.service.AppointmentService;
import HealthAPI.service.ClientService;
import HealthAPI.service.TimeSlotService;
import HealthAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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
    public Appointment createAppointment(@RequestParam Long userId,
                                         @RequestParam Long timeSlotId,
                                         @RequestParam Long clientId) {
        User user = userService.getUserById(userId);
        TimeSlot timeSlot = timeSlotService.getTimeSlotById(timeSlotId);
        Client client = clientService.getClientById(clientId);
        Appointment appointment = Appointment.builder()
                .user(user)
                .timeSlot(timeSlot)
                .client(client)
                .build();
        return appointmentService.createAppointment(appointment);
    }

    @GetMapping("/{id}")
    public BaseResponse<Appointment> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = appointmentService.findAppointmentById(id);
        if (appointment != null) {
            return new BaseResponse<>(appointment, "FETCHED");
        } else {
            return new BaseResponse<>(null, "NOT FOUND");
        }
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Appointment> deleteAppointmentById(@PathVariable Long id) {
        Appointment appointment = appointmentService.deleteAppointmentById(id);
        if (appointment != null) {
            return new BaseResponse<>(appointment, "DELETED");
        } else {
            return new BaseResponse<>(null, "NOT FOUND");
        }
    }

    @PatchMapping("/{id}")
    public BaseResponse<Appointment> restoreById(@PathVariable Long id) {
        ResponseEntity<?> responseEntity = appointmentService.restoreById(id);
        if (responseEntity != null) {
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return new BaseResponse<>((Appointment) responseEntity.getBody(), "RESTORED");
            } else if (responseEntity.getStatusCode().is4xxClientError()) {
                return new BaseResponse<>(null, Objects.requireNonNull(responseEntity.getBody()).toString());
            }
        }
        return new BaseResponse<>(null, "NOT FOUND");
    }

    @GetMapping("/getAllByClientId")
    public AppointmentResponse getAllAppointmentByClientId(@RequestParam Long clientId) {
        List<Appointment> appointmentList = appointmentService.findAllByClientId(clientId);

        if (appointmentList == null || appointmentList.size() == 0) {
            return new AppointmentResponse(null, "NOT AVAILABLE");
        } else {
            return new AppointmentResponse(appointmentList, "FETCHED");
        }
    }

    @GetMapping("/getAllByUserId")
    public AppointmentResponse getAllAppointmentByUserId(@RequestParam Long userId) {
        List<Appointment> appointmentList = appointmentService.findAllByUserId(userId);
        if (appointmentList == null || appointmentList.size() == 0) {
            return new AppointmentResponse(null, "NOT AVAILABLE");
        } else {
            return new AppointmentResponse(appointmentList, "FETCHED");
        }
    }

}