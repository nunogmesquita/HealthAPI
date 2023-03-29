package HealthAPI.controller;

import HealthAPI.converter.UserConverter;
import HealthAPI.dto.user.UserCreateDto;
import HealthAPI.dto.user.UserDto;
import HealthAPI.dto.user.UserUpdateDto;
import HealthAPI.messages.Responses;
import HealthAPI.model.User;
import HealthAPI.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserCreateDto user) {
        UserDto savedUser = userService.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/myaccount")
    public ResponseEntity<UserDto> getMyAccount(@NonNull HttpServletRequest request) {
        String userEmail = request.getUserPrincipal().getName();
        UserDto userDto = userService.getUserByEmail(userEmail);
        return ResponseEntity.ok(userDto);
    }

    @PatchMapping("/myaccount")
    public ResponseEntity<UserDto> updateMyAccount(@NonNull HttpServletRequest request,
                                                   @Valid @RequestBody UserUpdateDto userUpdateDto) {
        String userEmail = request.getUserPrincipal().getName();
        UserDto updatedUser = userService.updateUser(userEmail, userUpdateDto);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/editprofile/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @Valid @RequestBody UserUpdateDto userUpdateDto) {
        UserDto user = userService.updateUser(userId, userUpdateDto);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/viewprofiles")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/viewprofile/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        UserDto userDto = userConverter.fromUserToUserDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/viewinactiveprofiles")
    public ResponseEntity<List<UserDto>> getAllDeletedUsers() {
        List<UserDto> users = userService.getAllDeletedUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(Responses.DELETED_USER.formatted(userId));
    }

    @GetMapping("/restore/{userId}")
    public ResponseEntity<String> restoreAccount(@PathVariable Long userId) {
        userService.restoreUser(userId);
        return ResponseEntity.ok(Responses.RESTORED_USER.formatted(userId));
    }

}