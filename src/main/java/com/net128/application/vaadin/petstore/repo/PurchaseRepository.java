package com.net128.application.vaadin.petstore.repo;

import com.net128.application.vaadin.petstore.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.util.StringUtils;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    //List<Customer> findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrderById(String firstName, String lastName);
    default List<Purchase> filter(/*String name*/) {
//        if(StringUtils.isEmpty(name)) {
//            return findAll();
//        }
        return findAll();
    }
}
