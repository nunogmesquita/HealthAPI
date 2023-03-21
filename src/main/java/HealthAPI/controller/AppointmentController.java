package HealthAPI.controller;

import HealthAPI.dto.UserConverter;
import HealthAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class AppointmentController {
    private AppointmentService appointmentService;
    private AppointmentConverter appointmentConverter;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, AppointmentConverter appointmentConverter) {
        this.appointmentService = appointmentService;
        this.appointmentConverter = appointmentConverter;
    }

    @GetMapping("/appointment")
    public ResponseEntity<AppointmentDto> getAppointmentBooked(@PathVariable Long id) {
        AppointmentDto appointments = appointmentService.getAppointmentById;
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

}
