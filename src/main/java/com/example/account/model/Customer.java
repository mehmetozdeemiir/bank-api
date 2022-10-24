package com.example.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String lastName;

    private String phoneNumber;

    private String identificationNumber;

    private String address;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private City city;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Account> accounts;

    public Customer(String name, LocalDate dateOfBirth, City city, List<Account> accounts) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.city = city;
        this.accounts = accounts;
    }

    public Customer(String name, String lastName, String phoneNumber, String identificationNumber, String address, LocalDate dateOfBirth, City city) {
        this.name = name;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.identificationNumber = identificationNumber;
        this.address = address;
        this.city = city;
    }
}