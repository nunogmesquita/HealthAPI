package HealthAPI.dto.client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    @NotBlank(message = "Must have a street.")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Please insert a valid street.")
    private String street;

    @NotBlank(message = "Must have a city.")
    @Pattern(regexp = "^[A-Za-z,]+$", message = "Please insert a valid city.")
    private String city;

    @NotBlank(message = "Must have a zip code")
    @Pattern(regexp = "^\\d{4}(-\\d{3})?$",
            message = "Please insert a valid zipcode.")
    private String zipCode;

}