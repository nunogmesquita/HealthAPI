package HealthAPI.converter;

import HealthAPI.dto.user.ProfessionalDto;
import HealthAPI.dto.user.ProfessionalDto.ProfessionalDtoBuilder;
import HealthAPI.dto.user.UserCreateDto;
import HealthAPI.dto.user.UserDto;
import HealthAPI.dto.user.UserDto.UserDtoBuilder;
import HealthAPI.model.User;
import HealthAPI.model.User.UserBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-31T10:31:09+0100",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
@Component
public class UserConverterImpl implements UserConverter {

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

    @Override
    public ProfessionalDto fromUserToProfessionalDto(User user) {
        if ( user == null ) {
            return null;
        }

        ProfessionalDtoBuilder professionalDto = ProfessionalDto.builder();

        professionalDto.id( user.getId() );
        professionalDto.firstName( user.getFirstName() );
        professionalDto.lastName( user.getLastName() );
        if ( user.getSpeciality() != null ) {
            professionalDto.speciality( user.getSpeciality().name() );
        }

        return professionalDto.build();
    }

    @Override
    public User fromUserCreateDtoToUser(UserCreateDto userCreatedDto) {
        if ( userCreatedDto == null ) {
            return null;
        }

        UserBuilder user = User.builder();

        user.firstName( userCreatedDto.getFirstName() );
        user.lastName( userCreatedDto.getLastName() );
        user.email( userCreatedDto.getEmail() );
        user.password( userCreatedDto.getPassword() );
        user.speciality( userCreatedDto.getSpeciality() );
        user.role( userCreatedDto.getRole() );

        return user.build();
    }
}
