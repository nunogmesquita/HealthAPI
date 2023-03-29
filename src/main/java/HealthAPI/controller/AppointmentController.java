package HealthAPI.controller;

import HealthAPI.dto.appointment.AppointmentCreateDto;
import HealthAPI.dto.appointment.AppointmentDto;
import HealthAPI.dto.appointment.AppointmentUpdateDto;
import HealthAPI.messages.Responses;
import HealthAPI.service.AppointmentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("/register")
    public ResponseEntity<AppointmentDto> createAppointment(@NonNull HttpServletRequest request,
                                                            @Valid @RequestBody AppointmentCreateDto appointmentCreateDto) {
        String clientEmail = request.getUserPrincipal().getName();
        AppointmentDto appointmentDto = appointmentService.createAppointment(clientEmail, appointmentCreateDto);
        return ResponseEntity.ok(appointmentDto);
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<AppointmentDto> getAppointmentById(@PathVariable Long appointmentId) {
        AppointmentDto appointmentDto = appointmentService.findAppointmentById(appointmentId);
        return ResponseEntity.ok(appointmentDto);
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<String> deleteAppointmentById(@PathVariable Long appointmentId) {
        appointmentService.deleteAppointmentById(appointmentId);
        return ResponseEntity.ok(Responses.DELETED_APPOINTMENT.formatted(appointmentId));
    }

    @PatchMapping("/{appointmentId}")
    public ResponseEntity<AppointmentDto> updateAppointment(@PathVariable Long appointmentId,
                                                            @Valid @RequestBody AppointmentUpdateDto updatedAppointment) {
        AppointmentDto appointmentDto = appointmentService.updateAppointment(appointmentId, updatedAppointment);
        return ResponseEntity.ok(appointmentDto);
    }

    @GetMapping("/listbyuser/{userId}")
    public ResponseEntity<List<AppointmentDto>> getAllAppointmentByUserId(@PathVariable Long userId) {
        List<AppointmentDto> appointmentList = appointmentService.findAllByUserId(userId);
        return ResponseEntity.ok(appointmentList);
    }

    @GetMapping("/listbyclient/{clientId}")
    public ResponseEntity<List<AppointmentDto>> getAllAppointmentByClientId(@PathVariable Long clientId) {
        List<AppointmentDto> appointmentList = appointmentService.findAllByClientId(clientId);
        return ResponseEntity.ok(appointmentList);
    }

    @PostMapping("/restore/{appointmentId}")
    public ResponseEntity<AppointmentDto> restoreById(@PathVariable Long appointmentId) {
        AppointmentDto appointmentDto = appointmentService.restoreById(appointmentId);
        return new ResponseEntity<>(appointmentDto, HttpStatus.OK);
    }

}