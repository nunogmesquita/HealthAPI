package HealthAPI.converter;

import HealthAPI.dto.User.UserCreateDto;
import HealthAPI.dto.User.UserCreateDto.UserCreateDtoBuilder;
import HealthAPI.dto.User.UserDto;
import HealthAPI.dto.User.UserDto.UserDtoBuilder;
import HealthAPI.model.User;
import HealthAPI.model.User.UserBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-25T16:42:32+0000",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
@Component
public class UserConverterImpl implements UserConverter {

    @Override
    public User fromUserCreateDtoToUser(UserCreateDto userCreateDto) {
        if ( userCreateDto == null ) {
            return null;
        }

        UserBuilder user = User.builder();

        user.firstName( userCreateDto.getFirstName() );
        user.lastName( userCreateDto.getLastName() );
        user.email( userCreateDto.getEmail() );
        user.password( userCreateDto.getPassword() );
        user.speciality( userCreateDto.getSpeciality() );

        return user.build();
    }

    @Override
    public UserCreateDto fromUserToUserCreateDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserCreateDtoBuilder userCreateDto = UserCreateDto.builder();

        userCreateDto.firstName( user.getFirstName() );
        userCreateDto.lastName( user.getLastName() );
        userCreateDto.email( user.getEmail() );
        userCreateDto.password( user.getPassword() );
        userCreateDto.speciality( user.getSpeciality() );

        return userCreateDto.build();
    }

    @Override
    public UserDto fromUserToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDtoBuilder userDto = UserDto.builder();

        userDto.id( user.getId() );
        userDto.firstName( user.getFirstName() );
        userDto.lastName( user.getLastName() );
        userDto.email( user.getEmail() );
        userDto.speciality( user.getSpeciality() );

        return userDto.build();
    }

    @Override
    public User fromUserDtoToUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        UserBuilder user = User.builder();

        user.id( userDto.getId() );
        user.firstName( userDto.getFirstName() );
        user.lastName( userDto.getLastName() );
        user.email( userDto.getEmail() );
        user.speciality( userDto.getSpeciality() );

        return user.build();
    }
}
