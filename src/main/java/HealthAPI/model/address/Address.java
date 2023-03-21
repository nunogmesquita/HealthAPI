package HealthAPI.model.address;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Address {

    String street;

    City city;

    State state;

    @Column(nullable = false)
    @Pattern(regexp = "^\\d{4}(-\\d{3})?$", message = "Please insert a valid zipcode")
    String zipCode;

}