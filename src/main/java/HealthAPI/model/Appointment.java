package HealthAPI.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "appointments")
public class Appointment{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    TimeSlot timeSlot;
    @ManyToMany
    Client client;
    @Enumerated(EnumType.STRING)
    AppointmentType appointmentType;
    @Enumerated(EnumType.STRING)
    AppointmentSpecialty appointmentSpecialty;
}
