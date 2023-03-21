package HealthAPI.controller;

import HealthAPI.converter.UserConverter;
import HealthAPI.dto.UserCreateDto;
import HealthAPI.dto.UserDto;
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

    @Autowired
    public InfoController(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @GetMapping("/specialities")
    @Secured({"ROLE_ADMIN", "ROLE_COLLABORATOR"})
    public ResponseEntity<List<UserCreateDto>> myFirstEndPoint() {
        List<UserCreateDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{speciality}/healthcareproviders")
    public ResponseEntity<UserDto> getById(@PathVariable String speciality) {
        UserDto users = (UserDto) userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<UserCreateDto> getById(@PathVariable Long id) {
        UserCreateDto users = userService.getUserById(id);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}