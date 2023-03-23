package HealthAPI.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Builder
@Getter
@Setter
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    Date appointmentDate;

    @Column(nullable = false)
    AppointmentType appointmentType;

    @OneToOne
    Long timeSlotId;

    @OneToMany
    Long clientId;

    @OneToMany
    Long hcpId;

    @Column
    @Enumerated(EnumType.STRING)
    Status STATUS;
}