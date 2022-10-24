package com.example.account.dto.converter;

import com.example.account.dto.CustomerDto;
import com.example.account.model.Customer;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
@Data
@RequiredArgsConstructor
public class CustomerDtoConverter {

    private final CustomerAccountDtoConverter customerAccountDtoConverter;

   public CustomerDto convert(Customer customer){
       return new CustomerDto(
               customer.getName(),
               customer.getLastName(),
               customer.getDateOfBirth(),
               customer.getPhoneNumber(),
               customer.getIdentificationNumber(),
               customer.getAddress(),
               customer.getCity());
   }

   /*

   buda olabilir değişik denedim

   public CustomerDto converts(Customer customer){
        return CustomerDto.builder()
                .id(customer.getId())
                .address(customer.getAddress())
                .city(CityDto.valueOf(customer.getCity().name()))
                .name(customer.getName())
                .dateOfBirth(customer.getDateOfBirth())
                .build();
    }
    */
}