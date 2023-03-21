package HealthAPI.service;

import HealthAPI.dto.UserConverter;
import HealthAPI.dto.UserCreateDto;
import HealthAPI.dto.UserDto;
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
    public UserDto createUser(UserCreateDto userCreatedDto) {
        if(!userCreatedDto.getPassword().equals(userCreatedDto.getRetypePassword())){
            throw new IllegalArgumentException("Passawords do not match");
        }

        User user = userConverter.fromUserCreateDtoToEntity(userCreatedDto);
        user = userRepository.save(user);
        return userConverter.fromUserEntitytioUserDto(user);

    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.getReferenceById(userId);
        return userConverter.fromUserEntitytioUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.parallelStream()
                .map(userConverter::fromUserEntitytioUserDto)
                .toList();
        return userDtos;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        return null;
    }

    @Override
    public void deleteUser(Long userId) {

    }
}
