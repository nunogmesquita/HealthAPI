package HealthAPI.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import HealthAPI.converter.UserConverter;
import HealthAPI.dto.user.UserCreateDto;
import HealthAPI.dto.user.UserDto;
import HealthAPI.dto.user.UserUpdateDto;
import HealthAPI.model.Role;
import HealthAPI.model.Speciality;
import HealthAPI.model.User;
import HealthAPI.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @MockBean
    private UserConverter userConverter;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;


    @Test
    void testGetAllServices() {
        List<String> actualAllServices = userService.getAllServices();
        assertEquals(6, actualAllServices.size());
        assertEquals("FISIATRIA", actualAllServices.get(0));
        assertEquals("FISIOTERAPIA", actualAllServices.get(1));
        assertEquals("TERAPIA_DA_FALA", actualAllServices.get(2));
        assertEquals("NUTRIÇÃO", actualAllServices.get(3));
        assertEquals("PSIQUIATRIA", actualAllServices.get(4));
        assertEquals("PSICOLOGIA", actualAllServices.get(5));
    }

    @Test
    void testGetAllProfessionals() {
        when(userRepository.findByRoleAndDeleted((Role) any())).thenReturn(new ArrayList<>());
        assertTrue(userService.getAllProfessionals().isEmpty());
        verify(userRepository).findByRoleAndDeleted((Role) any());
    }


    @Test
    void testCreateUser() {
        when(userRepository.save((User) any())).thenReturn(new User());
        UserDto userDto = new UserDto();
        when(userConverter.fromUserToUserDto((User) any())).thenReturn(userDto);
        when(userConverter.fromUserCreateDtoToUser((UserCreateDto) any())).thenReturn(new User());
        assertSame(userDto, userService.createUser(
                new UserCreateDto("Jane", "Doe", "jane.doe@example.org", "iloveyou", Speciality.FISIATRIA, Role.ADMIN)));
        verify(userRepository).save((User) any());
        verify(userConverter).fromUserToUserDto((User) any());
        verify(userConverter).fromUserCreateDtoToUser((UserCreateDto) any());
    }

    @Test
    void testUpdateUser() {
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        UserDto userDto = new UserDto();
        when(userConverter.fromUserToUserDto((User) any())).thenReturn(userDto);
        assertSame(userDto, userService.updateUser(1L,
                new UserUpdateDto("Jane", "Doe", "jane.doe@example.org", "iloveyou", Speciality.FISIATRIA)));
        verify(userRepository).save((User) any());
        verify(userRepository).findById((Long) any());
        verify(userConverter).fromUserToUserDto((User) any());
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findByDeletedFalse()).thenReturn(new ArrayList<>());
        assertTrue(userService.getAllUsers().isEmpty());
        verify(userRepository).findByDeletedFalse();
    }

    @Test
    void testGetUserById() {
        User user = new User();
        when(userRepository.findByIdAndDeletedFalse((Long) any())).thenReturn(Optional.of(user));
        assertSame(user, userService.getUserById(1L));
        verify(userRepository).findByIdAndDeletedFalse((Long) any());
    }
    @Test
    void testDeleteUser() {
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findByIdAndDeletedFalse((Long) any())).thenReturn(Optional.of(new User()));
        userService.deleteUser(1L);
        verify(userRepository).save((User) any());
        verify(userRepository).findByIdAndDeletedFalse((Long) any());
    }

    @Test
    void testRestoreUser() {
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findByIdAndDeletedTrue((Long) any())).thenReturn(Optional.of(new User()));
        userService.restoreUser(1L);
        verify(userRepository).save((User) any());
        verify(userRepository).findByIdAndDeletedTrue((Long) any());
    }

}