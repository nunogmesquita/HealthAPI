package HealthAPI.service;

import HealthAPI.converter.AppointmentConverter;
import HealthAPI.dto.AppointmentDto;
import HealthAPI.model.Appointment;
import HealthAPI.model.Client;
import HealthAPI.repository.AppointmentRepository;
import HealthAPI.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AppointmentServiceImpl implements AppointmentService{
    private AppointmentRepository appointmentRepository;
    private AppointmentConverter appointmentConverter;
    ClientRepository clientRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, ClientRepository clientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public AppointmentDto bookAppointment(AppointmentDto appointmentDto) {
        Client client = clientRepository.getReferenceById(appointmentDto.getClientId());
        Appointment appointment = appointmentConverter.fromAppointmentDtoToAppointment(appointment);

        appointment = appointmentRepository.save(appointment);

        return appointmentConverter.fromAppointmentToAppointmentDto(appointment);
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
    public AppointmentDto updateAppointment(Long id, AppointmentDto appointmentDto) {
        return null;
    }

    @Override
    public List<AppointmentDto> getAppointmentSpecialty() {
        return null;
    }

    @Override
    public List<AppointmentDto> getAppointmentType() {
        return null;
    }

    @Override
    public void deleteAppointment(Long appointmentId) {

    }
}
