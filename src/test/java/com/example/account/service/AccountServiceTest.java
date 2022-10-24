package com.example.account.service;

import com.example.account.TestSupport;
import com.example.account.dto.AccountDto;
import com.example.account.dto.CreateAccountRequest;
import com.example.account.dto.converter.AccountDtoConverter;
import com.example.account.model.Account;
import com.example.account.model.Customer;
import com.example.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.junit.Assert;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.*;

public class AccountServiceTest extends TestSupport {

    private AccountService accountService;

    private AccountRepository accountRepository;
    private CustomerService customerService;
    private AccountDtoConverter accountDtoConverter;
    private DirectExchange exchange;
    private AmqpTemplate rabbitTemplate;
    private KafkaTemplate kafkaTemplate;

    @BeforeEach
    public void setUp() throws Exception {
        accountRepository = Mockito.mock(AccountRepository.class);
        customerService = Mockito.mock(CustomerService.class);
        accountDtoConverter = Mockito.mock(AccountDtoConverter.class);
        exchange = Mockito.mock(DirectExchange.class);
        rabbitTemplate = Mockito.mock(AmqpTemplate.class);
        kafkaTemplate = Mockito.mock(KafkaTemplate.class);

        accountService = new AccountService(accountRepository,
                customerService,
                accountDtoConverter, exchange, rabbitTemplate, kafkaTemplate);
    }
    @Test
    public void whenCreateAccountCalledWithValidRequest_itShouldReturnValidAccountDto() {

        //given
        CreateAccountRequest createAccountRequest=generateCreateAccountRequest();
        Customer customer=generateCustomer();
        Account account=generateAccount(createAccountRequest);
        AccountDto accountDto=generateAccountDto();

        Mockito.when(customerService.getCustomerById(1L)).thenReturn(customer);
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        Mockito.when(accountDtoConverter.convert(account)).thenReturn(accountDto);

        AccountDto result = accountService.createAccount(createAccountRequest);

        Assert.assertEquals(result, accountDto);
        Mockito.verify(customerService).getCustomerById(1L);
        Mockito.verify(accountRepository).save(account);
        Mockito.verify(accountDtoConverter).convert(account);


    }

    @Test
    void updateAccount() {
    }

    @Test
    void getAllAccountsDto() {
    }

    @Test
    void getAccountById() {
    }

    @Test
    void deleteAccount() {
    }

    @Test
    void addMoney() {
    }

    @Test
    void withdrawMoney() {
    }
}