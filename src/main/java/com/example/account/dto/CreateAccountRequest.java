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
public class CreateAccountRequest {
    private Long customerId;
    private Long balance;
    private String bankBranch;
    private String iban;
    private Currency currency;
    private City city;
}
