package com.net128.application.vaadin.petstore.repo;

import com.net128.application.vaadin.petstore.model.Customer;
import com.net128.application.vaadin.petstore.model.Species;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpeciesRepository extends JpaRepository<Species, Long> {
    List<Species> findByNameContainingIgnoreCaseOrderById(String name);

    List<Species> findByOrderByNameAsc();
    default List<Species> findAllOrdered() {
        return findByOrderByNameAsc();
    }
}
