package com.net128.application.vaadin.petstore.repo;

import com.net128.application.vaadin.petstore.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.StringUtils;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrderById(String firstName, String lastName);
    default List<Customer> filter(String name) {
        if(StringUtils.isEmpty(name)) {
            return findAll();
        }
        return findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrderById(name, name);
    }

    List<Customer> findByOrderByLastNameAscFirstNameAsc();
    default List<Customer> findAllOrdered() {
        return findByOrderByLastNameAscFirstNameAsc();
    }
}
