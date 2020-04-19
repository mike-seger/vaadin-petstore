package com.net128.application.vaadin.petstore;

import com.net128.application.vaadin.petstore.model.Pet;
import com.net128.application.vaadin.petstore.model.Species;
import com.net128.application.vaadin.petstore.model.User;
import com.net128.application.vaadin.petstore.repo.PetRepository;
import com.net128.application.vaadin.petstore.repo.SpeciesRepository;
import com.net128.application.vaadin.petstore.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner loadData(UserRepository userRepository,
              PetRepository petRepository,
              SpeciesRepository speciesRepository) {
        return (args) -> {
            userRepository.save(new User("Mark", "Smith"));
            userRepository.save(new User("Jennifer", "Bates"));
            userRepository.save(new User("Diana", "Hewitt"));
            userRepository.save(new User("Albert", "Freeman"));

            speciesRepository.save(new Species("Cat"));
            speciesRepository.save(new Species("Dog"));

            petRepository.save(new Pet("Teddy", speciesRepository.findOneByName("Dog")));
            petRepository.save(new Pet("Baxter", speciesRepository.findOneByName("Dog")));
            petRepository.save(new Pet("Whiskers", speciesRepository.findOneByName("Cat")));
            petRepository.save(new Pet("Bella", speciesRepository.findOneByName("Cat")));
        };
    }
}
