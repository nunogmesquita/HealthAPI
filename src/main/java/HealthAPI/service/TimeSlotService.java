package HealthAPI.service;

import HealthAPI.dto.TimeSlot.WeeklyTimeSlotDto;
import HealthAPI.dto.User.UserDto;
import HealthAPI.model.Speciality;
import HealthAPI.model.TimeSlot;

import java.util.List;

public interface TimeSlotService {

    void generateWeeklyTimeSlots(WeeklyTimeSlotDto weeklyTimeSlotDto, UserDto userDto);

    List<TimeSlot> getAvailableTimeSlots(int page, int i);

    List<TimeSlot> getAvailableTimeSlotsByUser(Long userId, int page, int i);

    List<TimeSlot> getAvailableTimeSlotsBySpeciality(Speciality speciality, int page, int i);

    void deleteAllTimeSlotsByUser(Long userId);

    void deleteTimeSlot(Long id);

    void updateTimeSlot(Long id, TimeSlot updatedTimeSlot);

    TimeSlot getTimeSlotById(Long id);
}
