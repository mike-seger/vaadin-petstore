package com.net128.application.vaadin.petstore.repo;

import com.net128.application.vaadin.petstore.model.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.StringUtils;

import java.util.List;

public interface SpeciesRepository extends JpaRepository<Species, Long> {
    List<Species> findByNameContainingIgnoreCaseOrderById(String name);

    List<Species> findByOrderByNameAsc();
    default List<Species> findAllOrdered() {
        return findByOrderByNameAsc();
    }
    default List<Species> filter(String name) {
        List<Species> species;
        if (StringUtils.hasText(name)) {
            species = findByNameContainingIgnoreCaseOrderById(name);
        } else {
            species = findAllOrdered();
        }
        return species;
    }
}
