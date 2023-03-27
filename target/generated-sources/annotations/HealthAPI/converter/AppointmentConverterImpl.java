package HealthAPI.converter;

import HealthAPI.dto.AppointmentCreateDto;
import HealthAPI.dto.AppointmentCreateDto.AppointmentCreateDtoBuilder;
import HealthAPI.dto.AppointmentDto;
import HealthAPI.dto.AppointmentDto.AppointmentDtoBuilder;
import HealthAPI.model.Appointment;
import HealthAPI.model.Appointment.AppointmentBuilder;
import HealthAPI.model.Client;
import HealthAPI.model.Client.ClientBuilder;
import HealthAPI.model.Speciality;
import HealthAPI.model.TimeSlot;
import HealthAPI.model.TimeSlot.TimeSlotBuilder;
import HealthAPI.model.User;
import HealthAPI.model.User.UserBuilder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-27T15:48:01+0100",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
@Component
public class AppointmentConverterImpl implements AppointmentConverter {

    @Override
    public Appointment fromAppointmentCreateDtoToAppointment(AppointmentCreateDto appointmentCreateDto) {
        if ( appointmentCreateDto == null ) {
            return null;
        }

        AppointmentBuilder appointment = Appointment.builder();

        return appointment.build();
    }

    @Override
    public AppointmentCreateDto fromAppointmentToAppointmentCreateDto(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }

        AppointmentCreateDtoBuilder appointmentCreateDto = AppointmentCreateDto.builder();

        return appointmentCreateDto.build();
    }

    @Override
    public AppointmentDto fromAppointmentToAppointmentDto(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }

        AppointmentDtoBuilder appointmentDto = AppointmentDto.builder();

        appointmentDto.professional( appointmentUserFirstName( appointment ) );
        appointmentDto.year( appointmentTimeSlotYear( appointment ) );
        appointmentDto.month( appointmentTimeSlotMonth( appointment ) );
        LocalDateTime startTime = appointmentTimeSlotStartTime( appointment );
        if ( startTime != null ) {
            appointmentDto.hour( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( startTime ) );
        }
        appointmentDto.client( appointmentClientEmail( appointment ) );
        Speciality speciality = appointmentUserSpeciality( appointment );
        if ( speciality != null ) {
            appointmentDto.speciality( speciality.name() );
        }

        return appointmentDto.build();
    }

    @Override
    public Appointment fromAppointmentDtoAppointment(AppointmentDto appointmentDto) {
        if ( appointmentDto == null ) {
            return null;
        }

        AppointmentBuilder appointment = Appointment.builder();

        appointment.user( appointmentDtoToUser( appointmentDto ) );
        appointment.timeSlot( appointmentDtoToTimeSlot( appointmentDto ) );
        appointment.client( appointmentDtoToClient( appointmentDto ) );

        return appointment.build();
    }

    private String appointmentUserFirstName(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }
        User user = appointment.getUser();
        if ( user == null ) {
            return null;
        }
        String firstName = user.getFirstName();
        if ( firstName == null ) {
            return null;
        }
        return firstName;
    }

    private int appointmentTimeSlotYear(Appointment appointment) {
        if ( appointment == null ) {
            return 0;
        }
        TimeSlot timeSlot = appointment.getTimeSlot();
        if ( timeSlot == null ) {
            return 0;
        }
        int year = timeSlot.getYear();
        return year;
    }

    private int appointmentTimeSlotMonth(Appointment appointment) {
        if ( appointment == null ) {
            return 0;
        }
        TimeSlot timeSlot = appointment.getTimeSlot();
        if ( timeSlot == null ) {
            return 0;
        }
        int month = timeSlot.getMonth();
        return month;
    }

    private LocalDateTime appointmentTimeSlotStartTime(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }
        TimeSlot timeSlot = appointment.getTimeSlot();
        if ( timeSlot == null ) {
            return null;
        }
        LocalDateTime startTime = timeSlot.getStartTime();
        if ( startTime == null ) {
            return null;
        }
        return startTime;
    }

    private String appointmentClientEmail(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }
        Client client = appointment.getClient();
        if ( client == null ) {
            return null;
        }
        String email = client.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }

    private Speciality appointmentUserSpeciality(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }
        User user = appointment.getUser();
        if ( user == null ) {
            return null;
        }
        Speciality speciality = user.getSpeciality();
        if ( speciality == null ) {
            return null;
        }
        return speciality;
    }

    protected User appointmentDtoToUser(AppointmentDto appointmentDto) {
        if ( appointmentDto == null ) {
            return null;
        }

        UserBuilder user = User.builder();

        user.firstName( appointmentDto.getProfessional() );
        if ( appointmentDto.getSpeciality() != null ) {
            user.speciality( Enum.valueOf( Speciality.class, appointmentDto.getSpeciality() ) );
        }

        return user.build();
    }

    protected TimeSlot appointmentDtoToTimeSlot(AppointmentDto appointmentDto) {
        if ( appointmentDto == null ) {
            return null;
        }

        TimeSlotBuilder timeSlot = TimeSlot.builder();

        timeSlot.year( appointmentDto.getYear() );
        timeSlot.month( appointmentDto.getMonth() );
        if ( appointmentDto.getHour() != null ) {
            timeSlot.startTime( LocalDateTime.parse( appointmentDto.getHour() ) );
        }

        return timeSlot.build();
    }

    protected Client appointmentDtoToClient(AppointmentDto appointmentDto) {
        if ( appointmentDto == null ) {
            return null;
        }

        ClientBuilder client = Client.builder();

        client.email( appointmentDto.getClient() );

        return client.build();
    }
}
