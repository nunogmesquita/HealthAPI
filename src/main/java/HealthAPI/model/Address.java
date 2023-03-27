package HealthAPI.model;

import HealthAPI.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    String street;

    String state;

    String city;

    @Column(nullable = false)
    @Pattern(regexp = "^\\d{4}(-\\d{3})?$", message = "Please insert a valid zipcode.")
    String zipCode;

    @OneToOne
    User user;

}