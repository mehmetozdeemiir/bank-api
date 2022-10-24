package com.example.account.dto;

import com.example.account.model.City;
import com.example.account.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerAccountDto {
    private Long balance;
    private Currency currency;
    private String bankBranch;
    private String iban;
    private City city;
}
