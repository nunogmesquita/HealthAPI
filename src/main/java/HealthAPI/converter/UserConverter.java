package HealthAPI.converter;

import HealthAPI.dto.user.ProfessionalDto;
import HealthAPI.dto.user.UserCreateDto;
import HealthAPI.dto.user.UserDto;
import HealthAPI.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserConverter {

    UserDto fromUserToUserDto(User user);

    User fromUserDtoToUser(UserDto userDto);

    ProfessionalDto fromUserToProfessionalDto(User user);

    User fromUserCreateDtoToUser(UserCreateDto userCreatedDto);

}