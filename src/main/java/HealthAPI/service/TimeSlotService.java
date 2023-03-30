package HealthAPI.service;

import HealthAPI.dto.timeSlot.TimeSlotDto;
import HealthAPI.dto.timeSlot.TimeSlotUpdateDto;
import HealthAPI.dto.timeSlot.WeeklyTimeSlotDto;
import HealthAPI.model.Speciality;
import HealthAPI.model.TimeSlot;

import java.util.List;

public interface TimeSlotService {

    void generateWeeklyTimeSlots(WeeklyTimeSlotDto weeklyTimeSlotDto);

    void deleteTimeSlot(Long timeSlotId);

    TimeSlotDto updateTimeSlot(Long timeSlotId, TimeSlotUpdateDto updatedTimeSlot);

    List<TimeSlotDto> getAvailableTimeSlots(int page, int size);

    List<TimeSlotDto> getAvailableTimeSlotsByUser(Long userId, int page, int size);

    List<TimeSlotDto> getAvailableTimeSlotsBySpeciality(Speciality speciality, int page, int size);

    void deleteAllTimeSlotsByUser(Long userId);

    TimeSlot getTimeSlotById(Long timeSlotId);

}