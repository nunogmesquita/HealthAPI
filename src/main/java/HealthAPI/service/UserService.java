package HealthAPI.service;

import HealthAPI.dto.user.ProfessionalDto;
import HealthAPI.dto.user.UserCreateDto;
import HealthAPI.dto.user.UserDto;
import HealthAPI.dto.user.UserUpdateDto;

import java.util.List;

public interface UserService {

    List<String> getAllServices();

    List<ProfessionalDto> getAllProfessionals();

    UserDto createUser(UserCreateDto userCreatedDto);

    UserDto getUserByEmail(String userEmail);

    UserDto updateUser(Long userId, UserUpdateDto userUpdateDto);

    UserDto updateUser(String userEmail, UserUpdateDto userUpdateDto);

    List<UserDto> getAllUsers();

    UserDto getUserById(Long userId);

    List<UserDto> getAllDeletedUsers();

    void deleteUser(Long userId);

    void restoreUser(Long userId);

}