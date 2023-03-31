package HealthAPI.controller;

import HealthAPI.dto.user.UserCreateDto;
import HealthAPI.dto.user.UserDto;
import HealthAPI.dto.user.UserUpdateDto;
import HealthAPI.exception.UnauthorizedAction;
import HealthAPI.messages.Responses;
import HealthAPI.service.UserServiceImpl;
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
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @Secured("ROLE_ADMIN")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody UserCreateDto user) {
        return userService.createUser(user);
    }

    @Secured({"ROLE_ADMIN", "ROLE_HEALTHCAREPROVIDER"})
    @GetMapping("/myaccount")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getMyAccount(@NonNull HttpServletRequest header) {
        String userEmail = header.getUserPrincipal().getName();
        if ((header.getAttribute("ROLE")).toString().compareTo("VIEWER") == 0) {
            throw new UnauthorizedAction();
        }
        return userService.getUserByEmail(userEmail);
    }

    @Secured({"ROLE_ADMIN", "ROLE_HEALTHCAREPROVIDER"})
    @PatchMapping("/myaccount")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateMyAccount(@NonNull HttpServletRequest request,
                                   @Valid @RequestBody UserUpdateDto userUpdateDto) {
        String userEmail = request.getUserPrincipal().getName();
        if ((request.getAttribute("ROLE")).toString().compareTo("VIEWER") == 0) {
            throw new UnauthorizedAction();
        }
        return userService.updateUser(userEmail, userUpdateDto);
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUser(@PathVariable Long userId, @Valid @RequestBody UserUpdateDto userUpdateDto) {
        return userService.updateUser(userId, userUpdateDto);
    }

    @Secured({"ROLE_ADMIN", "ROLE_HEALTHCAREPROVIDER"})
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @Cacheable(value = "getAllUsers")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @Secured({"ROLE_ADMIN", "ROLE_HEALTHCAREPROVIDER"})
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Cacheable(value = "getUserById", key = "#userId")
    public UserDto getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @Secured({"ROLE_ADMIN", "ROLE_HEALTHCAREPROVIDER"})
    @GetMapping("/inactive")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAllDeletedUsers() {
        return userService.getAllDeletedUsers();
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteAccount(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return Responses.DELETED_USER.formatted(userId);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{userId}/restore")
    @ResponseStatus(HttpStatus.OK)
    public String restoreAccount(@PathVariable Long userId) {
        userService.restoreUser(userId);
        return Responses.RESTORED_USER.formatted(userId);
    }

}