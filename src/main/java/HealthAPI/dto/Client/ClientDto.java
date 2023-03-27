package HealthAPI.dto.Client;

import HealthAPI.model.Gender;
import HealthAPI.model.Address;
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

    private int NIF;

    private Address address;

}