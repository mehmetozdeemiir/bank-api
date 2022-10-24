package com.example.account.dto;

import com.example.account.model.City;
import com.example.account.model.Currency;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
    private Long balance;
    private Currency currency;
    private String iban;
    private City city;
    private String bankBranch;
    private AccountCustomerDto accountCustomerDto;
}
