package HealthAPI.dto.client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private String street;

    private String city;

    @NotBlank(message = "Must have a zip code")
    @Pattern(regexp = "^\\d{4}(-\\d{3})?$",
            message = "Please insert a valid zipcode.")
    private String zipCode;

}