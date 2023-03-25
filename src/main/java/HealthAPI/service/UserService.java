package HealthAPI.service;


import HealthAPI.dto.User.UserCreateDto;
import HealthAPI.dto.User.UserDto;
import HealthAPI.model.User;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    User getUserById(Long userId);

    UserDto getUserByToken(String jwt);

    UserCreateDto createUser(@Valid UserCreateDto user);

    UserDto updateUser(Long id, UserCreateDto userCreateDto);

    void deleteUser(Long userId);

    String getAllServices();
}
