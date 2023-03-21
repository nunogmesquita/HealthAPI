package HealthAPI.service;

import HealthAPI.model.Appointment;
import HealthAPI.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AppointmentServiceImpl implements AppointmentService{

    private AppointmentRepository appointmentRepository;
    private AppointmentConverter appointmentConverter;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, AppointmentConverter appointmentConverter) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentConverter = appointmentConverter;
    }

    @Override
    public AppointmentDto bookAppointment(AppointmentCreateDto appointment) {

        if(!userCreatedDto.getPassword().equals(userCreatedDto.getRetypePassword())){
            throw new IllegalArgumentException("Invalid login.");
        }
        return null;
    }

    @Override
    public AppointmentDto getAppointmentById(Long appointmentId) {
        Appointment appointment = appointmentRepository.getReferenceById(appointmentId);
        return appointmentConverter.fromAppointmentEntityToAppointmentDto(appointment);
    }

    @Override
    public AppointmentDto updateAppointment(AppointmentDto appointmentDto) {
        List<Appointment> appointmentsList = appointmentRepository.getReferenceById();

        return new appointementDto;
    }

    @Override
    public void deleteAppointment(Long appointmentId) {

    }
}
