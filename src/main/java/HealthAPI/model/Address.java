package HealthAPI.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String street;

    String city;

    @Pattern(regexp = "^\\d{4}(-\\d{3})?$", message = "Please insert a valid zipcode.")
    String zipCode;

    @OneToMany
    List<Client> clients;

}