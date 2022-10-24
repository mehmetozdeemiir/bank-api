package com.example.account.model;

import lombok.*;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "account")
public class Account{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long balance;

    private String bankBranch;

    private String iban;

    @Enumerated(EnumType.STRING)
    private City city;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;


    public Account(Long id, Long balance, String bankBranch, String iban, City city, Currency currency) {
        this.id = id;
        this.balance = balance;
        this.bankBranch = bankBranch;
        this.iban = iban;
        this.city = city;
        this.currency = currency;
    }
}
