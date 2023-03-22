package HealthAPI.controller;

import HealthAPI.converter.UserConverter;
import HealthAPI.dto.*;
import HealthAPI.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
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

    @GetMapping("/all")
    @Secured({"ROLE_ADMIN", "ROLE_COLLABORATOR"})
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_COLLABORATOR"})
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/myAccount")
    public ResponseEntity<UserDto> getMyAccount(@NonNull HttpServletRequest request) {
        String jwt = request.getHeader("Authorization").substring(7);
        UserDto user = userService.getUserByToken(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
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
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserCreateDto userCreateDto) {
        UserDto user = userService.updateUser(id, userCreateDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //TODO update my account
    /*
    @PatchMapping("/myAccount")
    @Secured({"ROLE_ADMIN", "ROLE_COLLABORATOR", "ROLE_HEALTHCAREPROVIDERS"})
    public ResponseEntity<UpdateUserDto> getMyAccount(@NonNull HttpServletRequest request, @Valid @RequestBody UpdateUserDto updateUserDto) {
        String jwt = request.getHeader("Authorization").substring(7);
        UserDto user = userService.getUserByToken(jwt);
        UpdateUserDto userUpdated = userService.updateMyAccount(user)
        return new ResponseEntity<>(user, HttpStatus.OK);
    }*/

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("User has been deleted, mdf!", HttpStatus.OK);
    }
}

