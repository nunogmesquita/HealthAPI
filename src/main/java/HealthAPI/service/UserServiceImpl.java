package HealthAPI.service;

import HealthAPI.converter.UserConverter;
import HealthAPI.dto.UpdateUserDto;
import HealthAPI.dto.UserCreateDto;
import HealthAPI.dto.UserDto;
import HealthAPI.model.User;
import HealthAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static HealthAPI.model.Role.HEALTHCAREPROVIDER;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private UserConverter userConverter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.parallelStream()
                .map(userConverter::fromUserToUserDto)
                .toList();
        return userDtos;
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.getReferenceById(userId);
        return userConverter.fromUserToUserDto(user);
    }

    public User getUserByToken(String jwt){
        return userRepository.findByToken(jwt);
    }

    @Override
    public UserCreateDto createUser(UserCreateDto userCreatedDto) {
        User user = userConverter.fromUserCreateDtoToUser(userCreatedDto);
        user = userRepository.save(user);
        return userConverter.fromUserToUserCreateDto(user);
    }

    @Override
    public UserDto updateUser(Long id, UserCreateDto userCreateDto) {
        User user = userRepository.getReferenceById(id);
        user.setFirstName(userCreateDto.getFirstName());
        user.setLastName(userCreateDto.getLastName());
        user.setPassword(userCreateDto.getPassword());
        user.setRole(userCreateDto.getRole());
        user.setEmail(userCreateDto.getEmail());
        userRepository.save(user);
        return userConverter.fromUserToUserDto(user);
    }

    @Override
    public UserDto updateMyAccount(User user, UpdateUserDto updateUserDto) {
        user.setFirstName(updateUserDto.getFirstName());
        user.setLastName(updateUserDto.getLastName());
        user.setEmail(updateUserDto.getEmail());
        user.setPassword(updateUserDto.getPassword());
        userRepository.save(user);
        return userConverter.fromUserToUserDto(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<User> getHealthCareProviders() {
        List<User> hcp = userRepository.findByRole(HEALTHCAREPROVIDER);
        hcp.forEach(user -> user.toString());
        return hcp.stream().toList();
    }

}