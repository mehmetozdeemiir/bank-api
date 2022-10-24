package com.example.account;

import com.example.account.dto.AccountCustomerDto;
import com.example.account.dto.AccountDto;
import com.example.account.dto.CreateAccountRequest;
import com.example.account.model.Account;
import com.example.account.model.City;
import com.example.account.model.Currency;
import com.example.account.model.Customer;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestSupport {
    public CreateAccountRequest generateCreateAccountRequest(){
        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setCustomerId(1L);
        createAccountRequest.setBalance(1000L);
        createAccountRequest.setBankBranch("Bahçelievler");
        createAccountRequest.setIban("TR660006100519786457841326");
        createAccountRequest.setCurrency(Currency.EUR);
        createAccountRequest.setCity(City.İstanbul);
        return createAccountRequest;
    }

    public Customer generateCustomer() {
        return Customer.builder()
                .id(1L)
                .city(City.İstanbul)
                .dateOfBirth(LocalDate.now())
                .name("Mehmet")
                .address("süper adres")
                .lastName("Özdemir")
                .phoneNumber("05548688280")
                .identificationNumber("29770078538")
                .accounts(generateListAccount())
                .build();
    }

    public Account generateAccount(CreateAccountRequest accountRequest){
        return Account.builder()
                .id(accountRequest.getCustomerId())
                .iban(accountRequest.getIban())
                .balance(accountRequest.getBalance())
                .currency(accountRequest.getCurrency())
                .bankBranch(accountRequest.getBankBranch())
                .city(accountRequest.getCity())
                .build();
    }

    public static List<Account> generateListAccount(){
        return IntStream.range(0,5).mapToObj(i-> new Account(
                1L+i,
                1000L+ i,
                "bankBranch"+i,
                "TR660006100519786457841326"+i,
                City.İstanbul ,
                Currency.EUR,
                Customer.builder().id(1L)
                        .city(City.İstanbul)
                        .dateOfBirth(LocalDate.now())
                        .name("Mehmet")
                        .address("süper adres")
                        .lastName("Özdemir")
                        .phoneNumber("05548688280")
                        .identificationNumber("29770078538").build())).collect(Collectors.toList());
    }

    public AccountDto generateAccountDto(){
        return AccountDto.builder()
                .balance(1000L)
                .iban("TR660006100519786457841326")
                .balance(1000L)
                .currency(Currency.EUR)
                .bankBranch("Bahçelievler")
                .city(City.İstanbul)
                .accountCustomerDto(generateAccountCustomerDto())
                .build();
    }

    public AccountCustomerDto generateAccountCustomerDto(){
        return AccountCustomerDto.builder()
                .name("Mehmet")
                .lastName("Özdemir")
                .dateOfBirth(LocalDate.now())
                .phoneNumber("05548688280")
                .identificationNumber("29770078538")
                .address("süper adres")
                .city(City.İstanbul)
                .build();
    }


}
