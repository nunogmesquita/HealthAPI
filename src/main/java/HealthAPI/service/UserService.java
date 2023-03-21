package HealthAPI.service;


import HealthAPI.dto.UserCreateDto;
import HealthAPI.dto.UserDto;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {
    UserDto createUser(@Valid UserCreateDto user);
    UserDto getUserById(Long userId);
    List<UserDto> getAllUsers();
    UserDto updateUser(UserDto userDot);
    void deleteUser(Long userId);
}
