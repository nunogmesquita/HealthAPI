package HealthAPI.controller;

import HealthAPI.converter.UserConverter;
import HealthAPI.dto.user.UserCreateDto;
import HealthAPI.dto.user.UserDto;
import HealthAPI.dto.user.UserUpdateDto;
import HealthAPI.messages.Responses;
import HealthAPI.service.UserService;
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
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserConverter userConverter;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody UserCreateDto user) {
        return userService.createUser(user);
    }

    @GetMapping("/myaccount")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getMyAccount(@NonNull HttpServletRequest request) {
        String userEmail = request.getUserPrincipal().getName();
        return userService.getUserByEmail(userEmail);
    }

    @PatchMapping("/myaccount")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateMyAccount(@NonNull HttpServletRequest request,
                                                   @Valid @RequestBody UserUpdateDto userUpdateDto) {
        String userEmail = request.getUserPrincipal().getName();
        return userService.updateUser(userEmail, userUpdateDto);
    }

    @PatchMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUser(@PathVariable Long userId, @Valid @RequestBody UserUpdateDto userUpdateDto) {
        return userService.updateUser(userId, userUpdateDto);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @Cacheable(value = "getAllUsers")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Cacheable(value = "getUserById", key = "#userId")
    public UserDto getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/inactive")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAllDeletedUsers() {
        return userService.getAllDeletedUsers();
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteAccount(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return Responses.DELETED_USER.formatted(userId);
    }

    @GetMapping("/{userId}/restore")
    @ResponseStatus(HttpStatus.OK)
    public String restoreAccount(@PathVariable Long userId) {
        userService.restoreUser(userId);
        return Responses.RESTORED_USER.formatted(userId);
    }

}