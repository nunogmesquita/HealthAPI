package HealthAPI.converter;

import HealthAPI.dto.User.ProfessionalDto;
import HealthAPI.dto.User.UserCreateDto;
import HealthAPI.dto.User.UserDto;
import HealthAPI.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserConverter {

    UserCreateDto fromUserToUserCreateDto(User user);

    User fromUserDtoToUser(UserDto userDto);

    UserDto fromUserToUserDto(User user);

    User fromProfessionalDtoToUser(ProfessionalDto professionalDto);

    ProfessionalDto fromUserToProfessionalDto(User user);

    User fromUserCreateDtoToUser(UserCreateDto userCreatedDto);
}