package HealthAPI.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Address {
    String street;
    String city;
    String state;
    String zipCode;

}
