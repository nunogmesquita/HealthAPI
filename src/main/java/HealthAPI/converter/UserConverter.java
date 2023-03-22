package HealthAPI.converter;

import HealthAPI.dto.UpdateUserDto;
import HealthAPI.dto.UserCreateDto;
import HealthAPI.dto.UserDto;
import HealthAPI.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserConverter {

    User fromUserCreateDtoToUser(UserCreateDto userCreateDto);

    UserCreateDto fromUserToUserCreateDto(User user);

    UserDto fromUserToUserDto(User user);

    User fromUserDtoToUser(UserDto userDto);

    UpdateUserDto fromUserToUpdateUserDto(User user);

    User fromUpdateUserDtoToUser(UpdateUserDto updateUserDto);

}