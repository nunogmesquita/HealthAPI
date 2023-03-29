package HealthAPI.service;

import HealthAPI.converter.UserConverter;
import HealthAPI.dto.user.ProfessionalDto;
import HealthAPI.dto.user.UserCreateDto;
import HealthAPI.dto.user.UserDto;
import HealthAPI.exception.UserNotFound;
import HealthAPI.messages.Responses;
import HealthAPI.model.Speciality;
import HealthAPI.model.User;
import HealthAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static HealthAPI.model.Role.HEALTHCAREPROVIDER;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Autowired
    public UserService(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    public List<String> getAllServices() {
        List<String> services = new ArrayList<>();
        for (Speciality speciality : Speciality.values()) {
            services.add(speciality.toString());
        }
        return services;
    }

    public List<ProfessionalDto> getAllProfessionals() {
        List<User> users = userRepository.findByRoleAndDeletedFalse(HEALTHCAREPROVIDER);
        return users.parallelStream()
                .map(userConverter::fromUserToProfessionalDto)
                .toList();
    }

    public UserDto createUser(UserCreateDto userCreatedDto) {
        User user = userConverter.fromUserCreateDtoToUser(userCreatedDto);
        user = userRepository.save(user);
        return userConverter.fromUserToUserDto(user);
    }

    public UserDto getUserByEmail(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow();
        return userConverter.fromUserToUserDto(user);
    }

    public UserDto updateUser(Long userId, UserCreateDto userCreateDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFound(Responses.USER_NOT_FOUND.formatted(userId)));
        user.setFirstName(userCreateDto.getFirstName());
        user.setLastName(userCreateDto.getLastName());
        user.setEmail(userCreateDto.getEmail());
        user.setPassword(userCreateDto.getPassword());
        user.setSpeciality(userCreateDto.getSpeciality());
        user.setRole(userCreateDto.getRole());
        userRepository.save(user);
        return userConverter.fromUserToUserDto(user);
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findByDeletedFalse();
        return users.parallelStream()
                .map(userConverter::fromUserToUserDto)
                .toList();
    }

    public User getUserById(Long userId) {
        return userRepository.findByIdAndDeletedFalse(userId)
                .orElseThrow(() -> new UserNotFound(Responses.USER_NOT_FOUND.formatted(userId)));
    }

    public List<UserDto> getAllDeletedUsers() {
        List<User> users = userRepository.findByDeletedTrue();
        return users.parallelStream()
                .map(userConverter::fromUserToUserDto)
                .toList();
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFound(Responses.USER_NOT_FOUND.formatted(userId)));
        user.markAsDeleted();
        userRepository.save(user);
    }

}