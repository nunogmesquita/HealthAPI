package HealthAPI.service;


import HealthAPI.dto.UserCreateDto;
import HealthAPI.dto.UserDto;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {
    UserCreateDto createUser(@Valid UserCreateDto user);
    UserCreateDto getUserById(Long userId);
    List<UserDto> getAllUsers();
    UserCreateDto updateUser(Long id, UserCreateDto userDot);

    void deleteUser(Long userId);
}
