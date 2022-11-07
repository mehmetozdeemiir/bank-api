package com.example.account.service;

import com.example.account.dto.CreateCustomerRequest;
import com.example.account.dto.CustomerDto;
import com.example.account.dto.UpdateCustomerRequest;
import com.example.account.dto.converter.CustomerDtoConverter;
import com.example.account.exceptions.CustomerNotFoundException;
import com.example.account.model.City;
import com.example.account.model.Customer;
import com.example.account.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDtoConverter customerDtoConverter;

    public CustomerDto createCustomer(CreateCustomerRequest customerRequest) {
        Customer customer = customerRepository.save(Customer.builder()
                .name(customerRequest.getName())
                .lastName(customerRequest.getLastName())
                .dateOfBirth(customerRequest.getDateOfBirth())
                .phoneNumber(customerRequest.getPhoneNumber())
                .identificationNumber(customerRequest.getIdentificationNumber())
                .address(customerRequest.getAddress())
                .city(customerRequest.getCity())
                .build());
        return customerDtoConverter.convert(customer);

    }

    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream().map(customerDtoConverter::convert).toList();
    }

    public CustomerDto getCustomerDtoById(Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        return customerOptional.map(customerDtoConverter::convert).orElseThrow(() -> new CustomerNotFoundException("Customer Not Found"));
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public CustomerDto updateCustomer(Long id, UpdateCustomerRequest customerRequest) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        customerOptional.ifPresent(customer -> {
            customer.setName(customerRequest.getName());
            customer.setLastName(customerRequest.getLastName());
            customer.setPhoneNumber(customerRequest.getPhoneNumber());
            customer.setCity(City.valueOf(customerRequest.getCity().name()));
            customer.setDateOfBirth(customerRequest.getDateOfBirth());
            customer.setAddress(customerRequest.getAddress());
            customerRepository.save(customer);
        });
        return customerOptional.map(customerDtoConverter::convert).orElseThrow(() -> new CustomerNotFoundException("Customer Not Found"));
    }

    protected Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer Not Found"));
    }

}
