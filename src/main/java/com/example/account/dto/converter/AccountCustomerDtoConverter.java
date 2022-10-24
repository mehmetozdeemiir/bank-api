package com.example.account.dto.converter;

import com.example.account.dto.AccountCustomerDto;
import com.example.account.dto.CustomerAccountDto;
import com.example.account.model.Account;
import com.example.account.model.Customer;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Builder
public class AccountCustomerDtoConverter {

    public AccountCustomerDto convertAccountCustomer(Customer customer){
        return new AccountCustomerDto(
                customer.getName(),
                customer.getLastName(),
                customer.getDateOfBirth(),
                customer.getPhoneNumber(),
                customer.getIdentificationNumber(),
                customer.getAddress(),
                customer.getCity());
    }


}
