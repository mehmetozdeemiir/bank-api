package com.example.account.dto;

import com.example.account.model.City;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCustomerRequest {
    private String name;
    private String lastName;
    @Pattern(regexp ="/^([1-9]|[12][0-9]|3[01])(|\\/|\\.|\\-|\\s)?(0[1-9]|1[12])\\2(19[0-9]{2}|200[0-9]|201[0-8])$/")
    private LocalDate dateOfBirth;
    @Pattern(regexp = "/^(0)([2348]{1})([0-9]{2})\\s?([0-9]{3})\\s?([0-9]{2})\\s?([0-9]{2})$/")
    private String phoneNumber;
    @Pattern(regexp = "^[1-9]{1}[0-9]{9}[02468]{1}$")
    private String identificationNumber;
    private String address;
    private City city;
}
