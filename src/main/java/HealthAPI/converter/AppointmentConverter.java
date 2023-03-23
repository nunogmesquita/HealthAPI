package HealthAPI.converter;

import HealthAPI.dto.AppointmentDto;
import HealthAPI.model.Appointment;
import HealthAPI.model.Client;
import org.mapstruct.Mapper;
@Mapper
public interface AppointmentConverter {

    AppointmentDto fromAppointmentDtoToAppointment(Appointment appointment);

    Appointment fromAppointmentToAppointmentDto(AppointmentDto appointmentDto, Client client);

}