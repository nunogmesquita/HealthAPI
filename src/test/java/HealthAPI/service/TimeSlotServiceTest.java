package HealthAPI.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import HealthAPI.converter.TimeSlotConverter;
import HealthAPI.converter.UserConverter;
import HealthAPI.dto.timeSlot.TimeSlotDto;
import HealthAPI.dto.timeSlot.TimeSlotUpdateDto;
import HealthAPI.dto.timeSlot.WeeklyTimeSlotDto;
import HealthAPI.dto.user.UserDto;
import HealthAPI.exception.ResourceNotFoundException;
import HealthAPI.model.Speciality;
import HealthAPI.model.TimeSlot;
import HealthAPI.model.User;
import HealthAPI.repository.TimeSlotRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TimeSlotService.class})
@ExtendWith(SpringExtension.class)
class TimeSlotServiceTest {
    @MockBean
    private UserConverter userConverter;

    @MockBean
    private TimeSlotConverter timeSlotConverter;

    @MockBean
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private TimeSlotService timeSlotService;

    @MockBean
    private UserService userService;
    @Test
    void testGenerateWeeklyTimeSlots() {
        when(userService.getUserById((Long) any())).thenReturn(new UserDto());
        when(userConverter.fromUserDtoToUser((UserDto) any()))
                .thenThrow(new ResourceNotFoundException("Resource Name", "Field Name", "Field Value"));
        assertThrows(ResourceNotFoundException.class,
                () -> timeSlotService.generateWeeklyTimeSlots(new WeeklyTimeSlotDto()));
        verify(userService).getUserById((Long) any());
        verify(userConverter).fromUserDtoToUser((UserDto) any());
    }
    @Test
    void testDeleteTimeSlot() {
        doThrow(new ResourceNotFoundException("Resource Name", "Field Name", "Field Value")).when(timeSlotRepository)
                .deleteById((Long) any());
        assertThrows(ResourceNotFoundException.class, () -> timeSlotService.deleteTimeSlot(1L));
        verify(timeSlotRepository).deleteById((Long) any());
    }
    @Test
    void testUpdateTimeSlot() {
        when(timeSlotRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> timeSlotService.updateTimeSlot(1L, new TimeSlotUpdateDto()));
        verify(timeSlotRepository).findById((Long) any());
    }
    @Test
    void testGetAvailableTimeSlotsByUser() {
        when(timeSlotRepository.findByUser_IdAndIsBookedFalse((Long) any(), (Pageable) any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));
        assertTrue(timeSlotService.getAvailableTimeSlotsByUser(1L, 1, 3).isEmpty());
        verify(timeSlotRepository).findByUser_IdAndIsBookedFalse((Long) any(), (Pageable) any());
    }
    @Test
    void testGetAvailableTimeSlotsBySpeciality() {
        when(timeSlotRepository.findByUser_SpecialityAndIsBookedFalse((Speciality) any(), (Pageable) any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));
        assertTrue(timeSlotService.getAvailableTimeSlotsBySpeciality(Speciality.FISIATRIA, 1, 3).isEmpty());
        verify(timeSlotRepository).findByUser_SpecialityAndIsBookedFalse((Speciality) any(), (Pageable) any());
    }
    @Test
    void testDeleteAllTimeSlotsByUser() {
        doNothing().when(timeSlotRepository).deleteAllByUser_Id((Long) any());
        timeSlotService.deleteAllTimeSlotsByUser(1L);
        verify(timeSlotRepository).deleteAllByUser_Id((Long) any());
    }
}

