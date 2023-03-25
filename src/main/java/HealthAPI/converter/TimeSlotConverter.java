package HealthAPI.converter;

import HealthAPI.dto.TimeSlot.WeeklyTimeSlotDto;
import HealthAPI.model.TimeSlot;
import org.mapstruct.Mapper;

@Mapper
public interface TimeSlotConverter {

    TimeSlot fromWeeklyTimeSlotDtoToTimeSlot(WeeklyTimeSlotDto weeklyTimeSlotDto);

    WeeklyTimeSlotDto fromTimeSlotToWeeklyTimeSlotDto(TimeSlot timeSlot);

}