package com.example.account.repository;

import com.example.account.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

 public interface CustomerRepository extends JpaRepository<Customer,String > {
}
