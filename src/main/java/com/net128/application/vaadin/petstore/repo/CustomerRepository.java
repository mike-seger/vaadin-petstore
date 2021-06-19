package com.net128.application.vaadin.petstore.repo;

import com.net128.application.vaadin.petstore.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.StringUtils;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrAddressContainingIgnoreCaseOrCityContainingIgnoreCaseOrderByFirstNameAscLastNameAscIdAsc
            (String firstName, String lastName, String address, String city);
    List<Customer> findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrAddressContainingIgnoreCaseOrCityContainingIgnoreCaseOrderByLastNameAscFirstNameAscIdAsc
            (String firstName, String lastName, String address, String city);
    default List<Customer> filter(String text) {
        if(!StringUtils.hasText(text)) {
            return findAllOrdered();
        }
        return findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrAddressContainingIgnoreCaseOrCityContainingIgnoreCaseOrderByFirstNameAscLastNameAscIdAsc(text, text, text, text);
    }

    List<Customer> findByOrderByLastNameAscFirstNameAscIdAsc();
    default List<Customer> findAllOrderedLastFirst() {
        return findByOrderByLastNameAscFirstNameAscIdAsc();
    }

    List<Customer> findByOrderByFirstNameAscLastNameAscIdAsc();
    default List<Customer> findAllOrdered() {
        return findByOrderByFirstNameAscLastNameAscIdAsc();
    }
}
