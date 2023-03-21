package HealthAPI.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public abstract class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    String userName;
    @Column(nullable = false, unique = true)
    String email;
    @Column(nullable = false)
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,16}$", message = "Password must contain at least 1 number (0-9),  1 uppercase letter,  1 lowercase letter, 1 non-alpha numeric number and have 8-16 characters with no space")
    String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Role role;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Token token;
    @Column(nullable = false)
    @ManyToMany
    List<Appointment> appointmentsList;

}
