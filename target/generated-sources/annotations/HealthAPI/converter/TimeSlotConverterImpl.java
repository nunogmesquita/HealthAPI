package HealthAPI.converter;

import HealthAPI.dto.TimeSlot.WeeklyTimeSlotDto;
import HealthAPI.dto.TimeSlot.WeeklyTimeSlotDto.WeeklyTimeSlotDtoBuilder;
import HealthAPI.model.TimeSlot;
import HealthAPI.model.TimeSlot.TimeSlotBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-25T16:42:32+0000",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
@Component
public class TimeSlotConverterImpl implements TimeSlotConverter {

    @Override
    public TimeSlot fromWeeklyTimeSlotDtoToTimeSlot(WeeklyTimeSlotDto weeklyTimeSlotDto) {
        if ( weeklyTimeSlotDto == null ) {
            return null;
        }

        TimeSlotBuilder timeSlot = TimeSlot.builder();

        return timeSlot.build();
    }

    @Override
    public WeeklyTimeSlotDto fromTimeSlotToWeeklyTimeSlotDto(TimeSlot timeSlot) {
        if ( timeSlot == null ) {
            return null;
        }

        WeeklyTimeSlotDtoBuilder weeklyTimeSlotDto = WeeklyTimeSlotDto.builder();

        return weeklyTimeSlotDto.build();
    }
}
