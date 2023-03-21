package HealthAPI.service;

import HealthAPI.converter.AppointmentConverter;
import HealthAPI.dto.AppointmentDto;
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
    public AppointmentDto bookAppointment(AppointmentDto appointment) {
        return null;
    }

    @Override
    public AppointmentDto getAppointmentById(Long appointmentId) {
        return null;
    }

    @Override
    public List<AppointmentDto> getAllAppointments() {
        return null;
    }

    @Override
    public AppointmentDto updateAppointment(AppointmentDto appointmentDto) {
        return null;
    }

    @Override
    public void deleteAppointment(Long appointmentId) {

    }
}
