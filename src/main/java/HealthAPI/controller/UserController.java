package HealthAPI.controller;

import HealthAPI.converter.UserConverter;
import HealthAPI.dto.AuthenticationRequest;
import HealthAPI.dto.AuthenticationResponse;
import HealthAPI.dto.UserCreateDto;
import HealthAPI.dto.UserDto;
import HealthAPI.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private UserConverter userConverter;

    @Autowired
    public UserController(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(userService.authenticate(request));
    }

    @GetMapping("/all")
    @Secured({"ROLE_ADMIN", "ROLE_COLLABORATOR"})
    public ResponseEntity<List<UserCreateDto>> myFirstEndPoint() {
        List<UserCreateDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_COLLABORATOR"})
    public ResponseEntity<UserCreateDto> getId(@PathVariable Long id) {
        UserCreateDto users = userService.getUserById(id);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/myAccount")
    @Secured({"ROLE_ADMIN", "ROLE_COLLABORATOR", "ROLE_HEALTHCAREPROVIDERS"})
    public ResponseEntity<UserCreateDto> getById(@PathVariable Long id) {
        UserCreateDto users = userService.getUserById(id);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<UserCreateDto> createUser(@Valid @RequestBody UserCreateDto user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {
                System.out.println(error.getObjectName() + " - " + error.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        UserCreateDto savedUser = userService.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<UserCreateDto> updateUser(@PathVariable Long id) {
        UserCreateDto users = userService.getUserById(id);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PatchMapping("/myAccount")
    @Secured({"ROLE_ADMIN", "ROLE_COLLABORATOR", "ROLE_HEALTHCAREPROVIDERS"})
    public ResponseEntity<UserCreateDto> getMyAccount(@PathVariable Long id) {
        UserCreateDto users = userService.getUserById(id);
        return new ResponseEntity<>(users, HttpStatus.OK);
        //TODO exceto o role
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<UserCreateDto> deleteAccount(@PathVariable Long id) {
        UserCreateDto users = userService.getUserById(id);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}

