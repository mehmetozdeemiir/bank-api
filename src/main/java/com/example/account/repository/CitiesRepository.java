package com.example.account.repository;

import com.example.account.model.Cities;
import org.springframework.data.repository.CrudRepository;

public interface CitiesRepository extends CrudRepository<Cities,Long> {
}
