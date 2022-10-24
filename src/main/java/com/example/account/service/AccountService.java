package com.example.account.service;

import com.example.account.dto.*;
import com.example.account.dto.converter.AccountDtoConverter;
import com.example.account.exceptions.AccountNotFoundException;
import com.example.account.exceptions.CustomerNotFoundException;
import com.example.account.model.Account;
import com.example.account.model.Customer;
import com.example.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final AccountDtoConverter accountDtoConverter;
    private final DirectExchange exchange; //configdeki beanden geliyor
    private final AmqpTemplate rabbitTemplate; //rabbitmqda ilk queue topiğe değer yazabilmek için kullanılıyor amqptemplate( obje convert ediyor rabbitmq json olarak basmayı sağlıyor)
    private final KafkaTemplate<String , String> kafkaTemplate;

    @Value("${sample.rabbitmq.routingKey}")
    String routingKey;

    @Value("${sample.rabbitmq.queue}")
    String queueName;

    public AccountDto createAccount(CreateAccountRequest createAccountRequest) {
        Customer customer = customerService.getCustomerById(createAccountRequest.getCustomerId());
        if (Objects.isNull(customer.getId())) {
            throw new CustomerNotFoundException("Customer not found");
        }
        Account account = Account.builder()
                .balance(createAccountRequest.getBalance())
                .currency(createAccountRequest.getCurrency())
                .city(createAccountRequest.getCity())
                .bankBranch(createAccountRequest.getBankBranch())
                .iban(createAccountRequest.getIban())
                .customer(customer)
                .build();
        return accountDtoConverter.convert(accountRepository.save(account));
    }


    // bi bak
    public AccountDto updateAccount(Long id, UpdateAccountRequest request) {
        Customer customer = customerService.getCustomerById(request.getCustomerId());
        if (Objects.isNull(customer.getId())) {
            throw new CustomerNotFoundException("Customer not found");
        }
        Optional<Account> accountOptional = accountRepository.findById(id);
        accountOptional.ifPresent(account -> {
            account.setBalance(request.getBalance());
            account.setCity(request.getCity());
            account.setBankBranch(request.getBankBranch());
            account.setCurrency(request.getCurrency());
            account.setCustomer(customer);
            accountRepository.save(account);
        });
        return accountOptional.map(accountDtoConverter::convert).orElseThrow(()->new AccountNotFoundException("Account not found."));
    }


    //@Cacheable(value="accounts",key = "#id")
    public List<AccountDto> getAllAccountsDto() {
        return accountRepository.findAll().stream().map(accountDtoConverter::convert).toList();
    }


    public AccountDto getAccountById(Long id) {
        return accountRepository.findById(id).map(accountDtoConverter::convert).orElseThrow(()->new AccountNotFoundException("Account not found."));
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }


    public AccountDto addMoney(Long id, Long amount) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        accountOptional.ifPresentOrElse(account -> {
                    account.setBalance(account.getBalance() + amount);
                    accountRepository.save(account);
                },
                () -> log.error("Account not found"));
        return accountOptional.map(accountDtoConverter::convert).orElseThrow(() -> new AccountNotFoundException("Account Not Found"));
    }






    public AccountDto withdrawMoney(Long id, Long amount) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        accountOptional.ifPresentOrElse(account -> {
                    if (account.getBalance() > amount) {
                        account.setBalance(account.getBalance() - amount);
                        accountRepository.save(account);
                    } else {
                        log.error("Insufficient funds -> accountId: " + id + " balance: " + account.getBalance() + " amount: " + amount); } },
                    () -> log.error("Account not found"));
        return accountOptional.map(accountDtoConverter::convert).orElseThrow(() -> new AccountNotFoundException("Account Not Found"));
    }


    //aldığı isteği direkt olarak rabbitmq ye gönderiyor kurgu bu şekilde
    public void transferMoney(MoneyTransferRequest transferRequest) {
        rabbitTemplate.convertAndSend(exchange.getName(), routingKey, transferRequest);
    }


    //listener metod aynı zamanda consumer ıluyor
    @RabbitListener(queues = "${sample.rabbitmq.queue}")
    public void transferMoneyMessage(MoneyTransferRequest transferRequest) {
        Optional<Account> accountOptional = accountRepository.findById(transferRequest.getFromId());//gonderecek hesap
        accountOptional.ifPresentOrElse(account -> {
                    if (account.getBalance() > transferRequest.getAmount()) {
                        account.setBalance(account.getBalance() - transferRequest.getAmount());
                        accountRepository.save(account);
                        rabbitTemplate.convertAndSend(exchange.getName(), "secondRoute", transferRequest);
                    } else {
                        log.error("Insufficient funds -> accountId: " + transferRequest.getFromId() + " balance: " + account.getBalance() + " amount: " + transferRequest.getAmount());
                    }},
                () -> {
                    log.error("Account not found");
                    throw new AccountNotFoundException("Account Not Found");
        });
    }



    @RabbitListener(queues = "secondStepQueue")
    public void updateReceiverAccount(MoneyTransferRequest transferRequest) {
        Optional<Account> accountOptional = accountRepository.findById(transferRequest.getToId());//alıcı hesabı aldı
        accountOptional.ifPresentOrElse(account -> {
            if (transferRequest.getIban()!=null && transferRequest.getIban().equals(account.getIban())){
                account.setBalance(account.getBalance() + transferRequest.getAmount());
                accountRepository.save(account);
                rabbitTemplate.convertAndSend(exchange.getName(), "thirdRoute", transferRequest);
            }
            else{
                log.error("Iban does not belong to this person");
                Optional<Account> senderAccount = accountRepository.findById(transferRequest.getFromId());
                senderAccount.ifPresent(sender -> {
                    log.info("Money charge back to sender");
                    sender.setBalance(sender.getBalance() + transferRequest.getAmount());
                    accountRepository.save(sender);
                });
            }},
                () -> {
                    log.error("Receiver Account not found");
                    Optional<Account> senderAccount = accountRepository.findById(transferRequest.getFromId());
                    senderAccount.ifPresent(sender -> {
                        log.info("Money charge back to sender");
                        sender.setBalance(sender.getBalance() + transferRequest.getAmount());
                        accountRepository.save(sender);
                    });
                });
    }



    @RabbitListener(queues = "thirdStepQueue")
    public void finalizeTransfer(MoneyTransferRequest transferRequest) {
        Optional<Account> accountOptional = accountRepository.findById(transferRequest.getFromId());
        accountOptional.ifPresentOrElse(account ->
                {
                    String notificationMessage = "Dear customer %s \n  Your money transfer request has been succed. Your new balance is %s";
                    log.info("Sender(" + account.getId() + ") new account balance: " + account.getBalance());
                    String senderMessage= String.format(notificationMessage,account.getId(), account.getBalance());
                    kafkaTemplate.send("transfer-notification",senderMessage);
                },() -> log.error("Account not found"));

        Optional<Account> accountToOptional =accountRepository.findById(transferRequest.getToId());
        accountToOptional.ifPresentOrElse(account ->
                {
                    String notificationMessage = "Dear customer %s \n You received a money transfer from %s. Your new balance is %s";
                    System.out.println("Receiver(" + account.getId() +") new account balance: " + account.getBalance());
                    String receiverMessage = String.format(notificationMessage, account.getId(), transferRequest.getFromId(), account.getBalance());
                    kafkaTemplate.send("transfer-notification",  receiverMessage);
                }, () -> log.error("Account not found"));
    }



}