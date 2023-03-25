package HealthAPI.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @ManyToOne
    TimeSlot timeSlot;

    @ManyToOne
    Client client;

    @Column
    @Enumerated(EnumType.STRING)
    Status STATUS;

}