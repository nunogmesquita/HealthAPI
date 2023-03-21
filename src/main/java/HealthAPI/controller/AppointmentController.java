package HealthAPI.controller;

import HealthAPI.converter.AppointmentConverter;
import HealthAPI.dto.AppointmentDto;
import HealthAPI.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    private AppointmentService appointmentService;
    private AppointmentConverter appointmentConverter;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, AppointmentConverter appointmentConverter) {
        this.appointmentService = appointmentService;
        this.appointmentConverter = appointmentConverter;
    }

    @GetMapping("")
    public ResponseEntity<List<AppointmentDto>> getAppointmentsBooked() {
        List<AppointmentDto> appointments = appointmentService.getAllAppointments();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDto> getAppointment(@PathVariable Long id) {
        AppointmentDto appointments = appointmentService.getAppointmentById(id);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<AppointmentDto> newAppointment() {
        AppointmentDto appointments = appointmentService.bookAppointment();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
        //TODO JSON com especialidade, hcp e o tipo de serviço
        //TODO return horários disponíveis com respetivo id
        // Transporte?
    }

    @PostMapping("{id}")
    public ResponseEntity<AppointmentDto> bookAppointment(@PathVariable Long id) {
        AppointmentDto appointments = appointmentService.getAppointmentById(id);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
        // TODO return preço e tipo de pagamento
    }

    @PostMapping("{id}/pay/{type}")
    public ResponseEntity<AppointmentDto> bookAppointment(@PathVariable Long id, @PathVariable String type) {
        AppointmentDto appointments = appointmentService.getAppointmentById(id);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

}