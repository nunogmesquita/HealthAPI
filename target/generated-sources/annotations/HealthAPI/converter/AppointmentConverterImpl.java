package HealthAPI.converter;

import HealthAPI.dto.AppointmentDto;
import HealthAPI.dto.AppointmentDto.AppointmentDtoBuilder;
import HealthAPI.model.Appointment;
import HealthAPI.model.Appointment.AppointmentBuilder;
import HealthAPI.model.Client;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-25T16:56:02+0000",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
@Component
public class AppointmentConverterImpl implements AppointmentConverter {

    @Override
    public AppointmentDto fromAppointmentDtoToAppointment(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }

        AppointmentDtoBuilder appointmentDto = AppointmentDto.builder();

        appointmentDto.timeSlot( appointment.getTimeSlot() );

        return appointmentDto.build();
    }

    @Override
    public Appointment fromAppointmentToAppointmentDto(AppointmentDto appointmentDto, Client client) {
        if ( appointmentDto == null && client == null ) {
            return null;
        }

        AppointmentBuilder appointment = Appointment.builder();

        if ( appointmentDto != null ) {
            appointment.timeSlot( appointmentDto.getTimeSlot() );
        }
        if ( client != null ) {
            appointment.id( client.getId() );
        }

        return appointment.build();
    }
}
