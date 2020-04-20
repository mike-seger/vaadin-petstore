package com.net128.application.vaadin.petstore.repo;

import com.net128.application.vaadin.petstore.model.Pet;
import com.net128.application.vaadin.petstore.model.Species;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findBySpeciesIn(Collection<Species> species);
    default List<Pet> filter(Collection <Species> species) {
        if(species == null) {
            species = Collections.emptyList();
        } else {
            species = new ArrayList<>(species);
            species.remove(null);
        }
        if(species.isEmpty()) {
            return findAll();
        }
        return findBySpeciesIn(species);
    }
    default List<Pet> filter(Species ... species) {
        return filter(Arrays.asList(species));
    }
}
