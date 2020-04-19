package com.net128.application.vaadin.petstore.repo;

import com.net128.application.vaadin.petstore.model.Pet;
import com.net128.application.vaadin.petstore.model.Species;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findBySpecies(Species species);
}
