package com.example.account.dto;

import com.example.account.model.City;
import com.example.account.model.Currency;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class AccountDto implements Serializable {
    private Long id;
    private Long balance;
    private Currency currency;
    private String iban;
    private City city;
    private String bankBranch;
    private AccountCustomerDto accountCustomerDto;
}
