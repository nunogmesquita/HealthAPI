package HealthAPI.converter;

import HealthAPI.dto.TimeSlot.TimeSlotDto;
import HealthAPI.dto.TimeSlot.TimeSlotDto.TimeSlotDtoBuilder;
import HealthAPI.dto.TimeSlot.WeeklyTimeSlotDto;
import HealthAPI.dto.TimeSlot.WeeklyTimeSlotDto.WeeklyTimeSlotDtoBuilder;
import HealthAPI.model.TimeSlot;
import HealthAPI.model.TimeSlot.TimeSlotBuilder;
import HealthAPI.model.User;
import HealthAPI.model.User.UserBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-28T16:57:08+0100",
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

    @Override
    public TimeSlot fromTimeSlotDtoToTimeSlot(TimeSlotDto timeSlotDto) {
        if ( timeSlotDto == null ) {
            return null;
        }

        TimeSlotBuilder timeSlot = TimeSlot.builder();

        timeSlot.user( timeSlotDtoToUser( timeSlotDto ) );
        timeSlot.startTime( timeSlotDto.getStartTime() );
        timeSlot.endTime( timeSlotDto.getEndTime() );
        timeSlot.dayOfWeek( timeSlotDto.getDayOfWeek() );
        timeSlot.month( timeSlotDto.getMonth() );
        timeSlot.year( timeSlotDto.getYear() );

        return timeSlot.build();
    }

    @Override
    public TimeSlotDto fromTimeSlotToTimeSlotDto(TimeSlot timeSlot) {
        if ( timeSlot == null ) {
            return null;
        }

        TimeSlotDtoBuilder timeSlotDto = TimeSlotDto.builder();

        timeSlotDto.user( timeSlotUserFirstName( timeSlot ) );
        timeSlotDto.startTime( timeSlot.getStartTime() );
        timeSlotDto.endTime( timeSlot.getEndTime() );
        timeSlotDto.dayOfWeek( timeSlot.getDayOfWeek() );
        timeSlotDto.month( timeSlot.getMonth() );
        timeSlotDto.year( timeSlot.getYear() );

        return timeSlotDto.build();
    }

    protected User timeSlotDtoToUser(TimeSlotDto timeSlotDto) {
        if ( timeSlotDto == null ) {
            return null;
        }

        UserBuilder user = User.builder();

        user.firstName( timeSlotDto.getUser() );

        return user.build();
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
