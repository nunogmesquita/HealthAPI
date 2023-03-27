package HealthAPI.converter;

import HealthAPI.dto.AppointmentCreateDto;
import HealthAPI.dto.AppointmentDto;
import HealthAPI.model.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AppointmentConverter {

    Appointment fromAppointmentCreateDtoToAppointment(AppointmentCreateDto appointmentCreateDto);

    AppointmentCreateDto fromAppointmentToAppointmentCreateDto(Appointment appointment);

    @Mapping(source = "user.firstName", target = "professional")
    @Mapping(source = "timeSlot.year", target = "year")
    @Mapping(source = "timeSlot.month", target = "month")
    @Mapping(source = "timeSlot.startTime", target = "hour")
    @Mapping(source = "client.email", target = "client")
    @Mapping(source = "user.speciality", target = "speciality")
    AppointmentDto fromAppointmentToAppointmentDto(Appointment appointment);

    @Mapping(source = "professional", target = "user.firstName")
    @Mapping(source = "year", target = "timeSlot.year")
    @Mapping(source = "month", target = "timeSlot.month")
    @Mapping(source = "hour", target = "timeSlot.startTime")
    @Mapping(source = "client", target = "client.email")
    @Mapping(source = "speciality", target = "user.speciality")
    Appointment fromAppointmentDtoAppointment(AppointmentDto appointmentDto);

}