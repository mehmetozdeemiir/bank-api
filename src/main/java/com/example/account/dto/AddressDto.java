package com.example.account.dto;

import com.example.account.model.City;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class AddressDto {

    private String id;
    private City city;
    private String postCode;
}