package HealthAPI.dto;

import jakarta.validation.constraints.Pattern;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    String street;

    String city;

    @Pattern(regexp = "^\\d{4}(-\\d{3})?$", message = "Please insert a valid zipcode.")
    String zipCode;

}