package com.example.account;

import com.example.account.model.*;
import com.example.account.repository.AccountRepository;
import com.example.account.repository.AddressRepository;
import com.example.account.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.Arrays;
import java.util.HashSet;

@EnableCaching
@SpringBootApplication
public class AccountApplication implements CommandLineRunner {

	private final AccountRepository accountRepository;
	private final CustomerRepository customerRepository;
	private final AddressRepository addressRepository;

	public AccountApplication(AccountRepository accountRepository,
								   CustomerRepository customerRepository,
								   AddressRepository addressRepository) {
		this.accountRepository = accountRepository;
		this.customerRepository = customerRepository;
		this.addressRepository = addressRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Customer c1 = Customer.builder()
				.id("1234568")
				.name("Cagri")
				.address(Address.builder().city(City.ISTANBUL).postcode("456312").addressDetails("bu bir adrestir").build())
				.city(City.ISTANBUL)
				.dateOfBirth(1988)
				.build();


		Customer c2 = Customer.builder()
				.id("789456")
				.name("Semih")
				.city(City.MANISA)
				.address(Address.builder().city(City.MANISA).postcode("456312").addressDetails("bu bir adrestir 2").build())
				.dateOfBirth(2000)
				.build();

		Customer c3 = Customer.builder()
				.id("456238")
				.name("untpleax")
				.city(City.IZMIR)
				.address(Address.builder().city(City.IZMIR).postcode("456312").addressDetails("bu bir adrestir 3").build())
				.dateOfBirth(2005)
				.build();

		customerRepository.saveAll(Arrays.asList(c1,c2,c3));

		Account a1 = Account.builder()
				.id("100")
				.customerId("1234568")
				.city(City.ISTANBUL)
				.balance(1320.0)
				.currency(Currency.TRY)
				.build();
		Account a2 = Account.builder()
				.id("101")
				.customerId("789456")
				.city(City.ISTANBUL)
				.balance(7898.0)
				.currency(Currency.TRY)
				.build();
		Account a3 = Account.builder()
				.id("102")
				.customerId("456238")
				.city(City.ISTANBUL)
				.balance(120000.0)
				.currency(Currency.TRY)
				.build();

		accountRepository.saveAll(Arrays.asList(a1,a2,a3));
	}
}
