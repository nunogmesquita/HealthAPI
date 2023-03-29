package HealthAPI.converter;

import HealthAPI.dto.appointment.AppointmentDto;
import HealthAPI.model.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AppointmentConverter {

    @Mapping(source = "user.firstName", target = "professional")
    @Mapping(source = "user.speciality", target = "speciality")
    @Mapping(source = "client.email", target = "client")
    @Mapping(source = "timeSlot.startTime", target = "date")
    AppointmentDto fromAppointmentToAppointmentDto(Appointment appointment);

    @Mapping(source = "professional", target = "user.firstName")
    @Mapping(source = "speciality", target = "user.speciality")
    @Mapping(source = "client", target = "client.email")
    @Mapping(source = "date", target = "timeSlot.startTime")
    Appointment fromAppointmentDtoAppointment(AppointmentDto appointmentDto);

}