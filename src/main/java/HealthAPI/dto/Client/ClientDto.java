package HealthAPI.dto.Client;

import HealthAPI.dto.AddressDto;
import HealthAPI.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

    Long id;

    String fullName;

    int phoneNumber;

    String email;

    private LocalDate birthDate;

    private Gender gender;

    private Long NIF;

    private AddressDto address;

}