package HealthAPI.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class BaseController {

    @GetMapping("/")
    public String getHomepage() {
        return "Welcome to our API!";
    }

}