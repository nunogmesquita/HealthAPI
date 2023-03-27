package HealthAPI.service;

import HealthAPI.converter.UserConverter;
import HealthAPI.dto.User.UserCreateDto;
import HealthAPI.dto.User.UserDto;
import HealthAPI.model.Speciality;
import HealthAPI.model.User;
import HealthAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Autowired
    public UserService(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findByDeletedFalse();
        return users.parallelStream()
                .map(userConverter::fromUserToUserDto)
                .toList();
    }

    public UserDto getUserByToken(String jwt) {
        User user = userRepository.findByTokens(jwt);
        return userConverter.fromUserToUserDto(user);
    }

    public UserCreateDto createUser(UserCreateDto userCreatedDto) {
        User user = userConverter.fromUserCreateDtoToUser(userCreatedDto);
        user = userRepository.save(user);
        return userConverter.fromUserToUserCreateDto(user);
    }

    public UserDto updateUser(Long id, UserCreateDto userCreateDto) {
        User user = userRepository.getReferenceById(id);
        user.setFirstName(userCreateDto.getFirstName());
        user.setLastName(userCreateDto.getLastName());
        user.setEmail(userCreateDto.getEmail());
        user.setPassword(userCreateDto.getPassword());
        user.setSpeciality(userCreateDto.getSpeciality());
        userRepository.save(user);
        return userConverter.fromUserToUserDto(user);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        user.markAsDeleted();
        userRepository.save(user);
    }

    public String getAllServices() {
        return Speciality.values().toString();
        /*
        List<User> users = userRepository.findByDeletedFalseAndRole();
        return users.stream()
                .map(user -> toString())
                .toString();

         */
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow();
    }

}