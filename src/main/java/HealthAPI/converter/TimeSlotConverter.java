package HealthAPI.converter;

import HealthAPI.dto.timeSlot.TimeSlotDto;
import HealthAPI.model.TimeSlot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TimeSlotConverter {

    @Mapping(source = "user.firstName", target = "user")
    TimeSlotDto fromTimeSlotToTimeSlotDto(TimeSlot timeSlot);

}