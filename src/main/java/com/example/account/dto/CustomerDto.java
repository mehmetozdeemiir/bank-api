package com.example.account.dto;

import com.example.account.model.City;
import lombok.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {

    private String name;
    private String lastName;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String identificationNumber;
    private String address;
    private City city;

}