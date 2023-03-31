package HealthAPI.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import HealthAPI.converter.UserConverter;
import HealthAPI.dto.user.UserCreateDto;
import HealthAPI.dto.user.UserDto;
import HealthAPI.dto.user.UserUpdateDto;
import HealthAPI.exception.UserAlreadyActive;
import HealthAPI.exception.UserNotFound;
import HealthAPI.model.Role;
import HealthAPI.model.Speciality;
import HealthAPI.model.User;
import HealthAPI.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @MockBean
    private UserConverter userConverter;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    void testCreateUser() {
        when(userRepository.save((User) any())).thenReturn(new User());
        UserDto userDto = new UserDto();
        when(userConverter.fromUserToUserDto((User) any())).thenReturn(userDto);
        when(userConverter.fromUserCreateDtoToUser((UserCreateDto) any())).thenReturn(new User());
        assertSame(userDto, userServiceImpl.createUser(
                new UserCreateDto("Jane", "Doe", "jane.doe@example.org", "iloveyou", Speciality.FISIATRIA, Role.ADMIN)));
        verify(userRepository).save((User) any());
        verify(userConverter).fromUserToUserDto((User) any());
        verify(userConverter).fromUserCreateDtoToUser((UserCreateDto) any());
    }

    @Test
    void testCreateUser2() {
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userConverter.fromUserToUserDto((User) any())).thenThrow(new UserNotFound());
        when(userConverter.fromUserCreateDtoToUser((UserCreateDto) any())).thenThrow(new UserNotFound());
        assertThrows(UserNotFound.class, () -> userServiceImpl.createUser(
                new UserCreateDto("Jane", "Doe", "jane.doe@example.org", "iloveyou", Speciality.FISIATRIA, Role.ADMIN)));
        verify(userConverter).fromUserCreateDtoToUser((UserCreateDto) any());
    }

    @Test
    void testDeleteUser() {
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findByIdAndDeletedFalse((Long) any())).thenReturn(Optional.of(new User()));
        userServiceImpl.deleteUser(1L);
        verify(userRepository).save((User) any());
        verify(userRepository).findByIdAndDeletedFalse((Long) any());
    }

    @Test
    void testDeleteUser2() {
        when(userRepository.save((User) any())).thenThrow(new UserAlreadyActive(1L));
        when(userRepository.findByIdAndDeletedFalse((Long) any())).thenReturn(Optional.of(new User()));
        assertThrows(UserAlreadyActive.class, () -> userServiceImpl.deleteUser(1L));
        verify(userRepository).save((User) any());
        verify(userRepository).findByIdAndDeletedFalse((Long) any());
    }

    @Test
    void testDeleteUser3() {
        User user = mock(User.class);
        doNothing().when(user).markAsDeleted();
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findByIdAndDeletedFalse((Long) any())).thenReturn(ofResult);
        userServiceImpl.deleteUser(1L);
        verify(userRepository).save((User) any());
        verify(userRepository).findByIdAndDeletedFalse((Long) any());
        verify(user).markAsDeleted();
    }

    @Test
    void testDeleteUser5() {
        User user = mock(User.class);
        doThrow(new UserAlreadyActive(1L)).when(user).markAsDeleted();
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findByIdAndDeletedFalse((Long) any())).thenReturn(ofResult);
        assertThrows(UserAlreadyActive.class, () -> userServiceImpl.deleteUser(1L));
        verify(userRepository).findByIdAndDeletedFalse((Long) any());
        verify(user).markAsDeleted();
    }

    @Test
    void testGetAllDeletedUsers() {
        when(userRepository.findByDeleted((Boolean) any())).thenReturn(new ArrayList<>());
        assertTrue(userServiceImpl.getAllDeletedUsers().isEmpty());
        verify(userRepository).findByDeleted((Boolean) any());
    }


    /**
     * Method under test: {@link UserServiceImpl#getAllProfessionals()}
     */
    @Test
    void testGetAllProfessionals() {
        when(userRepository.findByRoleAndDeletedFalse((Role) any())).thenReturn(new ArrayList<>());
        assertTrue(userServiceImpl.getAllProfessionals().isEmpty());
        verify(userRepository).findByRoleAndDeletedFalse((Role) any());
    }

    @Test
    void testGetAllServices() {
        List<String> actualAllServices = userServiceImpl.getAllServices();
        assertEquals(6, actualAllServices.size());
        assertEquals("FISIATRIA", actualAllServices.get(0));
        assertEquals("FISIOTERAPIA", actualAllServices.get(1));
        assertEquals("TERAPIA_DA_FALA", actualAllServices.get(2));
        assertEquals("NUTRIÇÃO", actualAllServices.get(3));
        assertEquals("PSIQUIATRIA", actualAllServices.get(4));
        assertEquals("PSICOLOGIA", actualAllServices.get(5));
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findByDeleted((Boolean) any())).thenReturn(new ArrayList<>());
        assertTrue(userServiceImpl.getAllUsers().isEmpty());
        verify(userRepository).findByDeleted((Boolean) any());
    }

    @Test
    void testGetUserByEmail() {
        when(userRepository.findByEmail((String) any())).thenReturn(Optional.of(new User()));
        UserDto userDto = new UserDto();
        when(userConverter.fromUserToUserDto((User) any())).thenReturn(userDto);
        assertSame(userDto, userServiceImpl.getUserByEmail("jane.doe@example.org"));
        verify(userRepository).findByEmail((String) any());
        verify(userConverter).fromUserToUserDto((User) any());
    }

    @Test
    void testGetUserByEmail2() {
        when(userRepository.findByEmail((String) any())).thenReturn(Optional.of(new User()));
        when(userConverter.fromUserToUserDto((User) any())).thenThrow(new UserAlreadyActive(1L));
        assertThrows(UserAlreadyActive.class, () -> userServiceImpl.getUserByEmail("jane.doe@example.org"));
        verify(userRepository).findByEmail((String) any());
        verify(userConverter).fromUserToUserDto((User) any());
    }

    @Test
    void testGetUserById() {
        when(userRepository.findByIdAndDeletedFalse((Long) any())).thenReturn(Optional.of(new User()));
        UserDto userDto = new UserDto();
        when(userConverter.fromUserToUserDto((User) any())).thenReturn(userDto);
        assertSame(userDto, userServiceImpl.getUserById(1L));
        verify(userRepository).findByIdAndDeletedFalse((Long) any());
        verify(userConverter).fromUserToUserDto((User) any());
    }

    @Test
    void testGetUserById2() {
        when(userRepository.findByIdAndDeletedFalse((Long) any())).thenReturn(Optional.of(new User()));
        when(userConverter.fromUserToUserDto((User) any())).thenThrow(new UserAlreadyActive(1L));
        assertThrows(UserAlreadyActive.class, () -> userServiceImpl.getUserById(1L));
        verify(userRepository).findByIdAndDeletedFalse((Long) any());
        verify(userConverter).fromUserToUserDto((User) any());
    }

    @Test
    void testRestoreUser() {
        when(userRepository.findByIdAndDeletedFalse((Long) any())).thenReturn(Optional.of(new User()));
        when(userRepository.findByIdAndDeletedTrue((Long) any())).thenReturn(Optional.of(new User()));
        assertThrows(UserAlreadyActive.class, () -> userServiceImpl.restoreUser(1L));
        verify(userRepository).findByIdAndDeletedFalse((Long) any());
    }

    @Test
    void testRestoreUser2() {
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findByIdAndDeletedFalse((Long) any())).thenReturn(Optional.empty());
        when(userRepository.findByIdAndDeletedTrue((Long) any())).thenReturn(Optional.of(new User()));
        userServiceImpl.restoreUser(1L);
        verify(userRepository).save((User) any());
        verify(userRepository).findByIdAndDeletedFalse((Long) any());
        verify(userRepository).findByIdAndDeletedTrue((Long) any());
    }

    @Test
    void testRestoreUser3() {
        User user = mock(User.class);
        doNothing().when(user).restore();
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findByIdAndDeletedFalse((Long) any())).thenReturn(Optional.empty());
        when(userRepository.findByIdAndDeletedTrue((Long) any())).thenReturn(ofResult);
        userServiceImpl.restoreUser(1L);
        verify(userRepository).save((User) any());
        verify(userRepository).findByIdAndDeletedFalse((Long) any());
        verify(userRepository).findByIdAndDeletedTrue((Long) any());
        verify(user).restore();
    }

    @Test
    void testRestoreUser5() {
        User user = mock(User.class);
        doThrow(new UserAlreadyActive(1L)).when(user).restore();
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findByIdAndDeletedFalse((Long) any())).thenReturn(Optional.empty());
        when(userRepository.findByIdAndDeletedTrue((Long) any())).thenReturn(ofResult);
        assertThrows(UserAlreadyActive.class, () -> userServiceImpl.restoreUser(1L));
        verify(userRepository).findByIdAndDeletedFalse((Long) any());
        verify(userRepository).findByIdAndDeletedTrue((Long) any());
        verify(user).restore();
    }

    @Test
    void testUpdateUser() {
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        UserDto userDto = new UserDto();
        when(userConverter.fromUserToUserDto((User) any())).thenReturn(userDto);
        assertSame(userDto, userServiceImpl.updateUser(1L,
                new UserUpdateDto("Jane", "Doe", "jane.doe@example.org", "iloveyou", Speciality.FISIATRIA)));
        verify(userRepository).save((User) any());
        verify(userRepository).findById((Long) any());
        verify(userConverter).fromUserToUserDto((User) any());
    }

    @Test
    void testUpdateUser2() {
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        when(userConverter.fromUserToUserDto((User) any())).thenThrow(new UserAlreadyActive(1L));
        assertThrows(UserAlreadyActive.class, () -> userServiceImpl.updateUser(1L,
                new UserUpdateDto("Jane", "Doe", "jane.doe@example.org", "iloveyou", Speciality.FISIATRIA)));
        verify(userRepository).save((User) any());
        verify(userRepository).findById((Long) any());
        verify(userConverter).fromUserToUserDto((User) any());
    }

    @Test
    void testUpdateUser3() {
        User user = mock(User.class);
        doNothing().when(user).setEmail((String) any());
        doNothing().when(user).setFirstName((String) any());
        doNothing().when(user).setLastName((String) any());
        doNothing().when(user).setPassword((String) any());
        doNothing().when(user).setSpeciality((Speciality) any());
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        UserDto userDto = new UserDto();
        when(userConverter.fromUserToUserDto((User) any())).thenReturn(userDto);
        assertSame(userDto, userServiceImpl.updateUser(1L,
                new UserUpdateDto("Jane", "Doe", "jane.doe@example.org", "iloveyou", Speciality.FISIATRIA)));
        verify(userRepository).save((User) any());
        verify(userRepository).findById((Long) any());
        verify(user).setEmail((String) any());
        verify(user).setFirstName((String) any());
        verify(user).setLastName((String) any());
        verify(user).setPassword((String) any());
        verify(user).setSpeciality((Speciality) any());
        verify(userConverter).fromUserToUserDto((User) any());
    }

    @Test
    void testUpdateUser5() {
        User user = mock(User.class);
        doNothing().when(user).setEmail((String) any());
        doNothing().when(user).setFirstName((String) any());
        doNothing().when(user).setLastName((String) any());
        doNothing().when(user).setPassword((String) any());
        doNothing().when(user).setSpeciality((Speciality) any());
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        UserDto userDto = new UserDto();
        when(userConverter.fromUserToUserDto((User) any())).thenReturn(userDto);
        assertSame(userDto, userServiceImpl.updateUser(1L, new UserUpdateDto()));
        verify(userRepository).save((User) any());
        verify(userRepository).findById((Long) any());
        verify(userConverter).fromUserToUserDto((User) any());
    }

    @Test
    void testUpdateUser8() {
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findByEmail((String) any())).thenReturn(Optional.of(new User()));
        UserDto userDto = new UserDto();
        when(userConverter.fromUserToUserDto((User) any())).thenReturn(userDto);
        assertSame(userDto, userServiceImpl.updateUser("jane.doe@example.org",
                new UserUpdateDto("Jane", "Doe", "jane.doe@example.org", "iloveyou", Speciality.FISIATRIA)));
        verify(userRepository).save((User) any());
        verify(userRepository).findByEmail((String) any());
        verify(userConverter).fromUserToUserDto((User) any());
    }

    @Test
    void testUpdateUser9() {
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findByEmail((String) any())).thenReturn(Optional.of(new User()));
        when(userConverter.fromUserToUserDto((User) any())).thenThrow(new UserAlreadyActive(1L));
        assertThrows(UserAlreadyActive.class, () -> userServiceImpl.updateUser("jane.doe@example.org",
                new UserUpdateDto("Jane", "Doe", "jane.doe@example.org", "iloveyou", Speciality.FISIATRIA)));
        verify(userRepository).save((User) any());
        verify(userRepository).findByEmail((String) any());
        verify(userConverter).fromUserToUserDto((User) any());
    }

    @Test
    void testUpdateUser10() {
        User user = mock(User.class);
        doNothing().when(user).setEmail((String) any());
        doNothing().when(user).setFirstName((String) any());
        doNothing().when(user).setLastName((String) any());
        doNothing().when(user).setPassword((String) any());
        doNothing().when(user).setSpeciality((Speciality) any());
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findByEmail((String) any())).thenReturn(ofResult);
        UserDto userDto = new UserDto();
        when(userConverter.fromUserToUserDto((User) any())).thenReturn(userDto);
        assertSame(userDto, userServiceImpl.updateUser("jane.doe@example.org",
                new UserUpdateDto("Jane", "Doe", "jane.doe@example.org", "iloveyou", Speciality.FISIATRIA)));
        verify(userRepository).save((User) any());
        verify(userRepository).findByEmail((String) any());
        verify(user).setEmail((String) any());
        verify(user).setFirstName((String) any());
        verify(user).setLastName((String) any());
        verify(user).setPassword((String) any());
        verify(user).setSpeciality((Speciality) any());
        verify(userConverter).fromUserToUserDto((User) any());
    }

    @Test
    void testUpdateUser12() {
        User user = mock(User.class);
        doNothing().when(user).setEmail((String) any());
        doNothing().when(user).setFirstName((String) any());
        doNothing().when(user).setLastName((String) any());
        doNothing().when(user).setPassword((String) any());
        doNothing().when(user).setSpeciality((Speciality) any());
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findByEmail((String) any())).thenReturn(ofResult);
        UserDto userDto = new UserDto();
        when(userConverter.fromUserToUserDto((User) any())).thenReturn(userDto);
        assertSame(userDto, userServiceImpl.updateUser("jane.doe@example.org", new UserUpdateDto()));
        verify(userRepository).save((User) any());
        verify(userRepository).findByEmail((String) any());
        verify(userConverter).fromUserToUserDto((User) any());
    }
}
