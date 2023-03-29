package HealthAPI.converter;

import HealthAPI.dto.TimeSlot.TimeSlotDto;
import HealthAPI.dto.TimeSlot.WeeklyTimeSlotDto;
import HealthAPI.model.TimeSlot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TimeSlotConverter {

    TimeSlot fromWeeklyTimeSlotDtoToTimeSlot(WeeklyTimeSlotDto weeklyTimeSlotDto);

    WeeklyTimeSlotDto fromTimeSlotToWeeklyTimeSlotDto(TimeSlot timeSlot);

    @Mapping(source = "user", target = "user.firstName")
    TimeSlot fromTimeSlotDtoToTimeSlot(TimeSlotDto timeSlotDto);

    @Mapping(source = "user.firstName", target = "user")
    TimeSlotDto fromTimeSlotToTimeSlotDto(TimeSlot timeSlot);


}