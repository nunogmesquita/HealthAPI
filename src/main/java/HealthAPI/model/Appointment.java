package HealthAPI.model;

import jakarta.persistence.*;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    AppointmentType appointmentType;
    @OneToOne
    TimeSlot timeSlot;
    @OneToMany
    Client client;
    @OneToMany
    private User user;
}