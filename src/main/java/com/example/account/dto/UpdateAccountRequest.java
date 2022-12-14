package com.example.account.dto;

import com.example.account.model.City;
import com.example.account.model.Currency;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateAccountRequest  {
    private Long customerId;
    private Long balance;
    private String bankBranch;
    private String iban;
    private Currency currency;
    private City city;
}