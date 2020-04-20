package com.net128.application.vaadin.petstore.repo;

import java.util.List;

import com.net128.application.vaadin.petstore.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.StringUtils;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrderById(String firstName, String lastName);
    default List<Customer> filter(String name) {
        if(StringUtils.isEmpty(name)) {
            return findAll();
        }
        return findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrderById(name, name);
    }
}
