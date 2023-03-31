package HealthAPI.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import HealthAPI.exception.StartHourAfterFinishingHour;
import HealthAPI.exception.TimeSlotNotFound;
import HealthAPI.exception.TimeSlotsAlreadyExists;
import HealthAPI.exception.UserNotFound;
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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TimeSlotServiceImpl.class})
@ExtendWith(SpringExtension.class)
class TimeSlotServiceImplTest {
    @MockBean
    private TimeSlotConverter timeSlotConverter;

    @MockBean
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private TimeSlotServiceImpl timeSlotServiceImpl;

    @MockBean
    private UserConverter userConverter;

    @MockBean
    private UserServiceImpl userServiceImpl;

    @Test
    void testGenerateWeeklyTimeSlots4() {
        when(userServiceImpl.getUserById(Mockito.<Long>any())).thenReturn(new UserDto());
        when(userConverter.fromUserDtoToUser(Mockito.<UserDto>any())).thenReturn(new User());
        WeeklyTimeSlotDto weeklyTimeSlotDto = mock(WeeklyTimeSlotDto.class);
        when(weeklyTimeSlotDto.getUserId()).thenReturn(1L);
        when(weeklyTimeSlotDto.getDayOfWeeks()).thenReturn(new ArrayList<>());
        when(weeklyTimeSlotDto.getExcludedTimeRanges()).thenReturn(new ArrayList<>());
        when(weeklyTimeSlotDto.getDate()).thenReturn(LocalDate.now());
        when(weeklyTimeSlotDto.getFinishingHour()).thenReturn(LocalTime.MIDNIGHT);
        when(weeklyTimeSlotDto.getInitialHour()).thenReturn(LocalTime.MIDNIGHT);
        assertThrows(TimeSlotsAlreadyExists.class, () -> timeSlotServiceImpl.generateWeeklyTimeSlots(weeklyTimeSlotDto));
        verify(userServiceImpl).getUserById(Mockito.<Long>any());
        verify(userConverter).fromUserDtoToUser(Mockito.<UserDto>any());
        verify(weeklyTimeSlotDto).getUserId();
        verify(weeklyTimeSlotDto, atLeast(1)).getDate();
        verify(weeklyTimeSlotDto).getFinishingHour();
        verify(weeklyTimeSlotDto).getInitialHour();
        verify(weeklyTimeSlotDto).getDayOfWeeks();
        verify(weeklyTimeSlotDto, atLeast(1)).getExcludedTimeRanges();
    }

    @Test
    void testGenerateWeeklyTimeSlots6() {
        when(userServiceImpl.getUserById(Mockito.<Long>any())).thenReturn(new UserDto());
        when(userConverter.fromUserDtoToUser(Mockito.<UserDto>any())).thenReturn(new User());

        ArrayList<DayOfWeek> dayOfWeekList = new ArrayList<>();
        dayOfWeekList.add(DayOfWeek.MONDAY);
        WeeklyTimeSlotDto weeklyTimeSlotDto = mock(WeeklyTimeSlotDto.class);
        when(weeklyTimeSlotDto.getUserId()).thenReturn(1L);
        when(weeklyTimeSlotDto.getDayOfWeeks()).thenReturn(dayOfWeekList);
        when(weeklyTimeSlotDto.getExcludedTimeRanges()).thenReturn(new ArrayList<>());
        when(weeklyTimeSlotDto.getDate()).thenReturn(LocalDate.now());
        when(weeklyTimeSlotDto.getFinishingHour()).thenReturn(LocalTime.MIDNIGHT);
        when(weeklyTimeSlotDto.getInitialHour()).thenReturn(LocalTime.MIDNIGHT);
        assertThrows(TimeSlotsAlreadyExists.class, () -> timeSlotServiceImpl.generateWeeklyTimeSlots(weeklyTimeSlotDto));
        verify(userServiceImpl).getUserById(Mockito.<Long>any());
        verify(userConverter).fromUserDtoToUser(Mockito.<UserDto>any());
        verify(weeklyTimeSlotDto).getUserId();
        verify(weeklyTimeSlotDto, atLeast(1)).getDate();
        verify(weeklyTimeSlotDto, atLeast(1)).getFinishingHour();
        verify(weeklyTimeSlotDto, atLeast(1)).getInitialHour();
        verify(weeklyTimeSlotDto).getDayOfWeeks();
        verify(weeklyTimeSlotDto, atLeast(1)).getExcludedTimeRanges();
    }

    @Test
    void testDeleteTimeSlot() {
        doNothing().when(timeSlotRepository).deleteById(Mockito.<Long>any());
        when(timeSlotRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new TimeSlot()));
        timeSlotServiceImpl.deleteTimeSlot(1L);
        verify(timeSlotRepository).findById(Mockito.<Long>any());
        verify(timeSlotRepository).deleteById(Mockito.<Long>any());
    }

    @Test
    void testDeleteTimeSlot2() {
        doThrow(new StartHourAfterFinishingHour()).when(timeSlotRepository).deleteById(Mockito.<Long>any());
        when(timeSlotRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new TimeSlot()));
        assertThrows(StartHourAfterFinishingHour.class, () -> timeSlotServiceImpl.deleteTimeSlot(1L));
        verify(timeSlotRepository).findById(Mockito.<Long>any());
        verify(timeSlotRepository).deleteById(Mockito.<Long>any());
    }

    @Test
    void testDeleteTimeSlot3() {
        doNothing().when(timeSlotRepository).deleteById(Mockito.<Long>any());
        when(timeSlotRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        assertThrows(TimeSlotNotFound.class, () -> timeSlotServiceImpl.deleteTimeSlot(1L));
        verify(timeSlotRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testUpdateTimeSlot3() {
        when(timeSlotRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        assertThrows(TimeSlotNotFound.class, () -> timeSlotServiceImpl.updateTimeSlot(1L, new TimeSlotUpdateDto()));
        verify(timeSlotRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testUpdateTimeSlot4() {
        TimeSlot timeSlot = mock(TimeSlot.class);
        doNothing().when(timeSlot).setDayOfWeek(Mockito.<String>any());
        doNothing().when(timeSlot).setStartTime(Mockito.<LocalDateTime>any());
        Optional<TimeSlot> ofResult = Optional.of(timeSlot);
        when(timeSlotRepository.save(Mockito.<TimeSlot>any())).thenReturn(new TimeSlot());
        when(timeSlotRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        TimeSlotDto timeSlotDto = new TimeSlotDto();
        when(timeSlotConverter.fromTimeSlotToTimeSlotDto(Mockito.<TimeSlot>any())).thenReturn(timeSlotDto);
        assertSame(timeSlotDto,
                timeSlotServiceImpl.updateTimeSlot(1L, new TimeSlotUpdateDto(LocalDate.of(1970, 1, 1).atStartOfDay())));
        verify(timeSlotRepository).save(Mockito.<TimeSlot>any());
        verify(timeSlotRepository).findById(Mockito.<Long>any());
        verify(timeSlot).setDayOfWeek(Mockito.<String>any());
        verify(timeSlot).setStartTime(Mockito.<LocalDateTime>any());
        verify(timeSlotConverter).fromTimeSlotToTimeSlotDto(Mockito.<TimeSlot>any());
    }

    @Test
    void testUpdateTimeSlot5() {
        TimeSlot timeSlot = mock(TimeSlot.class);
        doNothing().when(timeSlot).setDayOfWeek(Mockito.<String>any());
        doNothing().when(timeSlot).setStartTime(Mockito.<LocalDateTime>any());
        Optional<TimeSlot> ofResult = Optional.of(timeSlot);
        when(timeSlotRepository.save(Mockito.<TimeSlot>any())).thenReturn(new TimeSlot());
        when(timeSlotRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(timeSlotConverter.fromTimeSlotToTimeSlotDto(Mockito.<TimeSlot>any()))
                .thenThrow(new StartHourAfterFinishingHour());
        assertThrows(StartHourAfterFinishingHour.class,
                () -> timeSlotServiceImpl.updateTimeSlot(1L, new TimeSlotUpdateDto(LocalDate.of(1970, 1, 1).atStartOfDay())));
        verify(timeSlotRepository).save(Mockito.<TimeSlot>any());
        verify(timeSlotRepository).findById(Mockito.<Long>any());
        verify(timeSlot).setDayOfWeek(Mockito.<String>any());
        verify(timeSlot).setStartTime(Mockito.<LocalDateTime>any());
        verify(timeSlotConverter).fromTimeSlotToTimeSlotDto(Mockito.<TimeSlot>any());
    }

    @Test
    void testGetAvailableTimeSlots() {
        when(timeSlotRepository.findByIsBookedFalse(Mockito.<Pageable>any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));
        assertTrue(timeSlotServiceImpl.getAvailableTimeSlots(1, 3).isEmpty());
        verify(timeSlotRepository).findByIsBookedFalse(Mockito.<Pageable>any());
    }

    @Test
    void testGetAvailableTimeSlotsByUser() {
        when(timeSlotRepository.findByUser_IdAndIsBookedFalse(Mockito.<Long>any(), Mockito.<Pageable>any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));
        when(userServiceImpl.getUserById(Mockito.<Long>any())).thenReturn(new UserDto());
        assertTrue(timeSlotServiceImpl.getAvailableTimeSlotsByUser(1L, 1, 3).isEmpty());
        verify(timeSlotRepository).findByUser_IdAndIsBookedFalse(Mockito.<Long>any(), Mockito.<Pageable>any());
        verify(userServiceImpl).getUserById(Mockito.<Long>any());
    }

    @Test
    void testGetAvailableTimeSlotsByUser2() {
        when(timeSlotRepository.findByUser_IdAndIsBookedFalse(Mockito.<Long>any(), Mockito.<Pageable>any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));
        when(userServiceImpl.getUserById(Mockito.<Long>any())).thenThrow(new StartHourAfterFinishingHour());
        assertThrows(StartHourAfterFinishingHour.class, () -> timeSlotServiceImpl.getAvailableTimeSlotsByUser(1L, 1, 3));
        verify(userServiceImpl).getUserById(Mockito.<Long>any());
    }

    @Test
    void testGetAvailableTimeSlotsBySpeciality() {
        when(timeSlotRepository.findByUser_SpecialityAndIsBookedFalse(Mockito.<Speciality>any(), Mockito.<Pageable>any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));
        assertTrue(timeSlotServiceImpl.getAvailableTimeSlotsBySpeciality(Speciality.STOMATOLOGY, 1, 3).isEmpty());
        verify(timeSlotRepository).findByUser_SpecialityAndIsBookedFalse(Mockito.<Speciality>any(),
                Mockito.<Pageable>any());
    }

    @Test
    void testDeleteAllTimeSlotsByUser() {
        doNothing().when(timeSlotRepository).deleteAllByUser_Id(Mockito.<Long>any());
        when(timeSlotRepository.findByUser_Id(Mockito.<Long>any())).thenReturn(Optional.of(new TimeSlot()));
        timeSlotServiceImpl.deleteAllTimeSlotsByUser(1L);
        verify(timeSlotRepository).findByUser_Id(Mockito.<Long>any());
        verify(timeSlotRepository).deleteAllByUser_Id(Mockito.<Long>any());
    }

    @Test
    void testDeleteAllTimeSlotsByUser2() {
        doThrow(new StartHourAfterFinishingHour()).when(timeSlotRepository).deleteAllByUser_Id(Mockito.<Long>any());
        when(timeSlotRepository.findByUser_Id(Mockito.<Long>any())).thenReturn(Optional.of(new TimeSlot()));
        assertThrows(StartHourAfterFinishingHour.class, () -> timeSlotServiceImpl.deleteAllTimeSlotsByUser(1L));
        verify(timeSlotRepository).findByUser_Id(Mockito.<Long>any());
        verify(timeSlotRepository).deleteAllByUser_Id(Mockito.<Long>any());
    }

    @Test
    void testGetTimeSlotById() {
        TimeSlot timeSlot = new TimeSlot();
        when(timeSlotRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(timeSlot));
        assertSame(timeSlot, timeSlotServiceImpl.getTimeSlotById(1L));
        verify(timeSlotRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testGetTimeSlotById2() {
        when(timeSlotRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        assertThrows(TimeSlotNotFound.class, () -> timeSlotServiceImpl.getTimeSlotById(1L));
        verify(timeSlotRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testGetTimeSlotById3() {
        when(timeSlotRepository.findById(Mockito.<Long>any())).thenThrow(new StartHourAfterFinishingHour());
        assertThrows(StartHourAfterFinishingHour.class, () -> timeSlotServiceImpl.getTimeSlotById(1L));
        verify(timeSlotRepository).findById(Mockito.<Long>any());
    }
}