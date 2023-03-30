package HealthAPI.controller;

import HealthAPI.dto.appointment.AppointmentCreateDto;
import HealthAPI.dto.appointment.AppointmentDto;
import HealthAPI.dto.appointment.AppointmentUpdateDto;
import HealthAPI.messages.Responses;
import HealthAPI.service.AppointmentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentServiceImpl appointmentService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public AppointmentDto createAppointment(@NonNull HttpServletRequest request,
                                            @Valid @RequestBody AppointmentCreateDto appointmentCreateDto) {
        String clientEmail = request.getUserPrincipal().getName();
        return appointmentService.createAppointment(clientEmail, appointmentCreateDto);
    }

    @Secured("ROLE_VIEWER")
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentDto> getMyAppointments(@NonNull HttpServletRequest request) {
        String clientEmail = request.getUserPrincipal().getName();
        return appointmentService.findAllByClientEmail(clientEmail);
    }

    @Secured({"ROLE_ADMIN", "ROLE_HEALTHCAREPROVIDER"})
    @GetMapping("/{appointmentId}")
    @ResponseStatus(HttpStatus.OK)
    @Cacheable(value = "getAppointmentById", key = "#appointmentId")
    public AppointmentDto getAppointmentById(@PathVariable Long appointmentId) {
        return appointmentService.findAppointmentById(appointmentId);
    }

    @Secured({"ROLE_ADMIN", "ROLE_HEALTHCAREPROVIDER"})
    @DeleteMapping("/{appointmentId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteAppointmentById(@PathVariable Long appointmentId) {
        appointmentService.deleteAppointmentById(appointmentId);
        return Responses.DELETED_APPOINTMENT.formatted(appointmentId);
    }

    @Secured({"ROLE_ADMIN", "ROLE_HEALTHCAREPROVIDER"})
    @PatchMapping("/{appointmentId}")
    @ResponseStatus(HttpStatus.OK)
    public AppointmentDto updateAppointment(@PathVariable Long appointmentId,
                                            @Valid @RequestBody AppointmentUpdateDto updatedAppointment) {
        return appointmentService.updateAppointment(appointmentId, updatedAppointment);
    }

    @Secured({"ROLE_ADMIN", "ROLE_HEALTHCAREPROVIDER"})
    @GetMapping("/listbyuser/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentDto> getAllAppointmentByUserId(@PathVariable Long userId) {
        return appointmentService.findAllByUserId(userId);
    }

    @Secured({"ROLE_ADMIN", "ROLE_HEALTHCAREPROVIDER"})
    @GetMapping("/listbyclient/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentDto> getAllAppointmentByClientId(@PathVariable Long clientId) {
        return appointmentService.findAllByClientId(clientId);
    }

    @Secured({"ROLE_ADMIN", "ROLE_HEALTHCAREPROVIDER"})
    @PostMapping("/{appointmentId}/restore")
    @ResponseStatus(HttpStatus.OK)
    public AppointmentDto restoreById(@PathVariable Long appointmentId) {
        return appointmentService.restoreById(appointmentId);
    }

}