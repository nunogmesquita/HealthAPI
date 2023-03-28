package HealthAPI.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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