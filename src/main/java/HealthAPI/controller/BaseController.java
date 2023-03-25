package HealthAPI.controller;

import HealthAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> getServices() {
        String Hcp = userService.getAllServices();
        return new ResponseEntity<>(Hcp, HttpStatus.OK);
    }

}