package HealthAPI.converter;

import HealthAPI.dto.timeSlot.TimeSlotDto;
import HealthAPI.dto.timeSlot.TimeSlotDto.TimeSlotDtoBuilder;
import HealthAPI.model.TimeSlot;
import HealthAPI.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-31T10:31:09+0100",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
@Component
public class TimeSlotConverterImpl implements TimeSlotConverter {

    @Override
    public TimeSlotDto fromTimeSlotToTimeSlotDto(TimeSlot timeSlot) {
        if ( timeSlot == null ) {
            return null;
        }

        TimeSlotDtoBuilder timeSlotDto = TimeSlotDto.builder();

        timeSlotDto.user( timeSlotUserFirstName( timeSlot ) );
        timeSlotDto.id( timeSlot.getId() );
        timeSlotDto.startTime( timeSlot.getStartTime() );

        return timeSlotDto.build();
    }

    private String timeSlotUserFirstName(TimeSlot timeSlot) {
        if ( timeSlot == null ) {
            return null;
        }
        User user = timeSlot.getUser();
        if ( user == null ) {
            return null;
        }
        String firstName = user.getFirstName();
        if ( firstName == null ) {
            return null;
        }
        return firstName;
    }
}
