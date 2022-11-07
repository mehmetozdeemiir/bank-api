package com.example.account.dto.converter;

import com.example.account.dto.AccountDto;
import com.example.account.model.Account;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@RequiredArgsConstructor
public class AccountDtoConverter {

    private final AccountCustomerDtoConverter accountCustomerDtoConverter;

   public AccountDto convert(Account account){
       return new AccountDto(
               account.getId(),
               account.getBalance(),
               account.getCurrency(),
               account.getIban(),
               account.getCity(),
               account.getBankBranch(),
               accountCustomerDtoConverter.convertAccountCustomer(account.getCustomer()));
   }
}