package HealthAPI.dto;

import HealthAPI.model.AppointmentSpecialty;
import HealthAPI.model.AppointmentType;
import HealthAPI.model.Client;
import HealthAPI.model.TimeSlot;

public class AppointmentDto {

    TimeSlot timeSlot;

    Client client;

    AppointmentType appointmentType;

    AppointmentSpecialty appointmentSpecialty;

}