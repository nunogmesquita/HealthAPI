package HealthAPI.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import HealthAPI.converter.TimeSlotConverter;
import HealthAPI.dto.timeSlot.WeeklyTimeSlotDto;
import HealthAPI.model.Speciality;
import HealthAPI.model.TimeSlot;
import HealthAPI.model.User;
import HealthAPI.repository.TimeSlotRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

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
    private TimeSlotConverter timeSlotConverter;

    @MockBean
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private TimeSlotService timeSlotService;

    @MockBean
    private UserService userService;
    @Test
    void testGenerateWeeklyTimeSlots() {
        when(userService.getUserById((Long) any())).thenReturn(new User());
        LocalDate date = LocalDate.ofEpochDay(1L);
        LocalTime initialHour = LocalTime.of(1, 1);
        LocalTime finishingHour = LocalTime.of(1, 1);
        ArrayList<DayOfWeek> dayOfWeeks = new ArrayList<>();
        timeSlotService.generateWeeklyTimeSlots(
                new WeeklyTimeSlotDto(date, initialHour, finishingHour, dayOfWeeks, new ArrayList<>(), 1L));
        verify(userService).getUserById((Long) any());
    }
    @Test
    void testDeleteTimeSlot() {
        doNothing().when(timeSlotRepository).deleteById((Long) any());
        timeSlotService.deleteTimeSlot(1L);
        verify(timeSlotRepository).deleteById((Long) any());
    }
    @Test
    void testGetAvailableTimeSlots() {
        when(timeSlotRepository.findByIsBookedFalse((Pageable) any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        assertTrue(timeSlotService.getAvailableTimeSlots(1, 3).isEmpty());
        verify(timeSlotRepository).findByIsBookedFalse((Pageable) any());
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
    @Test
    void testGetTimeSlotById() {
        TimeSlot timeSlot = new TimeSlot();
        when(timeSlotRepository.findById((Long) any())).thenReturn(Optional.of(timeSlot));
        assertSame(timeSlot, timeSlotService.getTimeSlotById(1L));
        verify(timeSlotRepository).findById((Long) any());
    }
}

