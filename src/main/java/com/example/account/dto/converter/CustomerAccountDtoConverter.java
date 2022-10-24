package com.example.account.dto.converter;

import com.example.account.dto.AccountDto;
import com.example.account.dto.CustomerAccountDto;
import com.example.account.model.Account;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Builder
public class CustomerAccountDtoConverter {

    public CustomerAccountDto convertToCustomerAccount(Account account){
        return new CustomerAccountDto(account.getBalance(),
                account.getCurrency(),
                account.getBankBranch(),
                account.getIban(),
                account.getCity());
    }
}
