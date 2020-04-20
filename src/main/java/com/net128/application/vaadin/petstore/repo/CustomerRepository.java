package com.net128.application.vaadin.petstore.repo;

import java.util.List;

import com.net128.application.vaadin.petstore.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrderById(String firstName, String lastName);
}
