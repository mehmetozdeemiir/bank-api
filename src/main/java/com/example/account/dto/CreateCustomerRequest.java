package com.example.account.dto;

import com.example.account.model.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCustomerRequest {
    private String name;
    private String lastName;
    private LocalDate dateOfBirth;
    @Pattern(regexp = "^(05)([0-9]{2})\\s?([0-9]{3})\\s?([0-9]{2})\\s?([0-9]{2})$")
    private String phoneNumber;
    @Pattern(regexp = "^[1-9]{1}[0-9]{9}[02468]{1}$")
    private String identificationNumber;
    private String address;
    private City city;
}
