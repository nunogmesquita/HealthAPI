package HealthAPI.service;


import HealthAPI.dto.UpdateUserDto;
import HealthAPI.dto.UserCreateDto;
import HealthAPI.dto.UserDto;
import HealthAPI.model.User;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDto getUserById(Long userId);

    User getUserByToken(String jwt);

    UserCreateDto createUser(@Valid UserCreateDto user);

    UserDto updateUser(Long id, UserCreateDto userCreateDto);

    UserDto updateMyAccount(User user, UpdateUserDto updateUserDto);

    void deleteUser(Long userId);

    List<User> getHealthCareProviders();
}
