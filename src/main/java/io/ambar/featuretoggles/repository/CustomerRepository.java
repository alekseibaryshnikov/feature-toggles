package io.ambar.featuretoggles.repository;

import org.springframework.data.repository.CrudRepository;

import io.ambar.featuretoggles.repository.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    
}