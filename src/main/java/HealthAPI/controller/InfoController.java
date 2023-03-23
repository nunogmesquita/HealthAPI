package HealthAPI.controller;

import HealthAPI.converter.UserConverter;
import HealthAPI.dto.AppointmentDto;
import HealthAPI.dto.UserCreateDto;
import HealthAPI.dto.UserDto;
import HealthAPI.service.AppointmentService;
import HealthAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class InfoController {
    private UserService userService;
    private UserConverter userConverter;
    private AppointmentService appointmentService;

    @Autowired
    public InfoController(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @GetMapping("/{speciality}/healthcareproviders")
    @Secured({"ROLE_ADMIN", "ROLE_COLLABORATOR"})
    public ResponseEntity<List<UserDto>> getAppointmentSpeciality (@PathVariable String speciality) {
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{appointment}")
    public ResponseEntity<AppointmentDto> getAppointmentType (@PathVariable String type) {
        AppointmentDto appointments = (AppointmentDto) appointmentService.getAppointmentType();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

}