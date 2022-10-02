package com.example.account.dto.converter;

import com.example.account.dto.CityDto;
import com.example.account.dto.CustomerDto;
import com.example.account.model.Customer;
import org.springframework.stereotype.Component;


@Component
public class CustomerDtoConverter {

    public CustomerDto convert(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setAddress(customer.getAddress());
        customerDto.setCity(CityDto.valueOf(customer.getCity().name()));
        customerDto.setName(customer.getName());
        customerDto.setDateOfBirth(customer.getDateOfBirth());
        return customerDto;
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