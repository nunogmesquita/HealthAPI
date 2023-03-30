package HealthAPI.service;

import HealthAPI.converter.UserConverter;
import HealthAPI.dto.user.ProfessionalDto;
import HealthAPI.dto.user.UserCreateDto;
import HealthAPI.dto.user.UserDto;
import HealthAPI.dto.user.UserUpdateDto;
import HealthAPI.exception.UserAlreadyActive;
import HealthAPI.exception.UserNotFound;
import HealthAPI.model.Speciality;
import HealthAPI.model.User;
import HealthAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static HealthAPI.model.Role.HEALTHCAREPROVIDER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

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

    public UserDto updateUser(Long userId, UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(userId)
                .orElseThrow();
        NullUtils.updateIfPresent(user::setFirstName, userUpdateDto.getFirstName());
        NullUtils.updateIfPresent(user::setLastName, userUpdateDto.getLastName());
        NullUtils.updateIfPresent(user::setEmail, userUpdateDto.getEmail());
        NullUtils.updateIfPresent(user::setPassword, userUpdateDto.getPassword());
        NullUtils.updateIfPresent(user::setSpeciality, userUpdateDto.getSpeciality());
        userRepository.save(user);
        return userConverter.fromUserToUserDto(user);
    }

    public UserDto updateUser(String userEmail, UserUpdateDto userUpdateDto) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow();
        NullUtils.updateIfPresent(user::setFirstName, userUpdateDto.getFirstName());
        NullUtils.updateIfPresent(user::setLastName, userUpdateDto.getLastName());
        NullUtils.updateIfPresent(user::setEmail, userUpdateDto.getEmail());
        NullUtils.updateIfPresent(user::setPassword, userUpdateDto.getPassword());
        NullUtils.updateIfPresent(user::setSpeciality, userUpdateDto.getSpeciality());
        userRepository.save(user);
        return userConverter.fromUserToUserDto(user);
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findByDeleted(false);
        return users.parallelStream()
                .map(userConverter::fromUserToUserDto)
                .toList();
    }

    public UserDto getUserById(Long userId) {
        User user = userRepository.findByIdAndDeletedFalse(userId)
                .orElseThrow(UserNotFound::new);
        return userConverter.fromUserToUserDto(user);
    }

    public List<UserDto> getAllDeletedUsers() {
        List<User> users = userRepository.findByDeleted(true);
        return users.parallelStream()
                .map(userConverter::fromUserToUserDto)
                .toList();
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findByIdAndDeletedFalse(userId)
                .orElseThrow(UserNotFound::new);
        user.markAsDeleted();
        userRepository.save(user);
    }

    public void restoreUser(Long userId) {
        if (userRepository.findByIdAndDeletedFalse(userId).isPresent()) {
            throw new UserAlreadyActive(userId);
        }
        User user = userRepository.findByIdAndDeletedTrue(userId)
                .orElseThrow(UserNotFound::new);
        user.restore();
        userRepository.save(user);
    }
}