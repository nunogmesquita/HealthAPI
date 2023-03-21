package HealthAPI.service;

import HealthAPI.converter.UserConverter;
import HealthAPI.dto.UserCreateDto;
import HealthAPI.model.User;
import HealthAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public UserCreateDto createUser(UserCreateDto userCreatedDto) {
        User user = userConverter.fromUserCreateDtoToUser(userCreatedDto);
        user = userRepository.save(user);
        return userConverter.fromUserToUserCreateDto(user);

    }

    @Override
    public UserCreateDto getUserById(Long userId) {
        User user = userRepository.getReferenceById(userId);
        return userConverter.fromUserToUserCreateDto(user);
    }

    @Override
    public List<UserCreateDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserCreateDto> userCreateDtos = users.parallelStream()
                .map(userConverter::fromUserToUserCreateDto)
                .toList();
        return userCreateDtos;
    }

    @Override
    public UserCreateDto updateUser(UserCreateDto userCreateDto) {
        return null;
    }

    @Override
    public void deleteUser(Long userId) {
    }

}