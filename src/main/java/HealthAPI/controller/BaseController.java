package HealthAPI.controller;

import HealthAPI.dto.User.ProfessionalDto;
import HealthAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class BaseController {

    private final UserService userService;

    @Autowired
    public BaseController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getHomepage() {
        return "Welcome to our API! :)";
    }

    @GetMapping("/services")
    public ResponseEntity<List<String>> getServices() {
        List<String> services = userService.getAllServices();
        return new ResponseEntity<>(services, HttpStatus.OK);
    }

    @GetMapping("/professionals")
    public ResponseEntity<List<ProfessionalDto>> getProfessionals() {
        List<ProfessionalDto> professionals = userService.getAllProfessionals();
        return new ResponseEntity<>(professionals, HttpStatus.OK);
    }

}