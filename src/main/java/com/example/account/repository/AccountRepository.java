package com.example.account.repository;

import com.example.account.model.Account;
import com.example.account.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account,Long> {

    //List<Account> findAllByBalanceGreaterThan(Double balance);

    //select * from account where currency=$(currency) and balance < 100
    //List<Account> findAllByCurrencyIsAndAndBalanceLessThan(Currency currency, Double balance);
}
