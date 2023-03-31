package HealthAPI.converter;

import HealthAPI.dto.appointment.AppointmentDto;
import HealthAPI.dto.appointment.AppointmentDto.AppointmentDtoBuilder;
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
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-31T11:41:56+0100",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class AppointmentConverterImpl implements AppointmentConverter {

    @Override
    public AppointmentDto fromAppointmentToAppointmentDto(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }

        AppointmentDtoBuilder appointmentDto = AppointmentDto.builder();

        appointmentDto.professional( appointmentUserFirstName( appointment ) );
        Speciality speciality = appointmentUserSpeciality( appointment );
        if ( speciality != null ) {
            appointmentDto.speciality( speciality.name() );
        }
        appointmentDto.client( appointmentClientEmail( appointment ) );
        appointmentDto.date( appointmentTimeSlotStartTime( appointment ) );
        appointmentDto.status( appointment.getStatus() );

        return appointmentDto.build();
    }

    @Override
    public Appointment fromAppointmentDtoAppointment(AppointmentDto appointmentDto) {
        if ( appointmentDto == null ) {
            return null;
        }

        AppointmentBuilder appointment = Appointment.builder();

        appointment.user( appointmentDtoToUser( appointmentDto ) );
        appointment.client( appointmentDtoToClient( appointmentDto ) );
        appointment.timeSlot( appointmentDtoToTimeSlot( appointmentDto ) );
        appointment.status( appointmentDto.getStatus() );

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

    protected Client appointmentDtoToClient(AppointmentDto appointmentDto) {
        if ( appointmentDto == null ) {
            return null;
        }

        ClientBuilder client = Client.builder();

        client.email( appointmentDto.getClient() );

        return client.build();
    }

    protected TimeSlot appointmentDtoToTimeSlot(AppointmentDto appointmentDto) {
        if ( appointmentDto == null ) {
            return null;
        }

        TimeSlotBuilder timeSlot = TimeSlot.builder();

        timeSlot.startTime( appointmentDto.getDate() );

        return timeSlot.build();
    }
}
