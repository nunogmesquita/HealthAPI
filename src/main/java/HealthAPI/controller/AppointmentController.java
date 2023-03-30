package HealthAPI.controller;

import HealthAPI.dto.appointment.AppointmentCreateDto;
import HealthAPI.dto.appointment.AppointmentDto;
import HealthAPI.dto.appointment.AppointmentUpdateDto;
import HealthAPI.messages.Responses;
import HealthAPI.service.AppointmentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
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

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public AppointmentDto createAppointment(@NonNull HttpServletRequest request,
                                                            @Valid @RequestBody AppointmentCreateDto appointmentCreateDto) {
        String clientEmail = request.getUserPrincipal().getName();
        AppointmentDto appointmentDto = appointmentService.createAppointment(clientEmail, appointmentCreateDto);
        return appointmentDto;
    }

    @GetMapping("/{appointmentId}")
    @ResponseStatus(HttpStatus.OK)
    @Cacheable(value = "getAppointmentById", key = "#appointmentId")
    public AppointmentDto getAppointmentById(@PathVariable Long appointmentId) {
        AppointmentDto appointmentDto = appointmentService.findAppointmentById(appointmentId);
        return appointmentDto;
    }

    @DeleteMapping("/{appointmentId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteAppointmentById(@PathVariable Long appointmentId) {
        appointmentService.deleteAppointmentById(appointmentId);
        return Responses.DELETED_APPOINTMENT.formatted(appointmentId);
    }

    @PatchMapping("/{appointmentId}")
    @ResponseStatus(HttpStatus.OK)
    public AppointmentDto updateAppointment(@PathVariable Long appointmentId,
                                                            @Valid @RequestBody AppointmentUpdateDto updatedAppointment) {
        AppointmentDto appointmentDto = appointmentService.updateAppointment(appointmentId, updatedAppointment);
        return appointmentDto;
    }

    @GetMapping("/listbyuser/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentDto> getAllAppointmentByUserId(@PathVariable Long userId) {
        List<AppointmentDto> appointmentList = appointmentService.findAllByUserId(userId);
        return appointmentList;
    }

    @GetMapping("/listbyclient/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentDto> getAllAppointmentByClientId(@PathVariable Long clientId) {
        List<AppointmentDto> appointmentList = appointmentService.findAllByClientId(clientId);
        return appointmentList;
    }

    @PostMapping("/{appointmentId}/restore")
    @ResponseStatus(HttpStatus.OK)
    public AppointmentDto restoreById(@PathVariable Long appointmentId) {
        AppointmentDto appointmentDto = appointmentService.restoreById(appointmentId);
        return appointmentDto;
    }

}