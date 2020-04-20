package com.net128.application.vaadin.petstore;

import com.net128.application.vaadin.petstore.model.Customer;
import com.net128.application.vaadin.petstore.model.Pet;
import com.net128.application.vaadin.petstore.model.Species;
import com.net128.application.vaadin.petstore.repo.PetRepository;
import com.net128.application.vaadin.petstore.repo.SpeciesRepository;
import com.net128.application.vaadin.petstore.repo.CustomerRepository;
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
    public CommandLineRunner loadData(CustomerRepository customerRepository,
                                      PetRepository petRepository,
                                      SpeciesRepository speciesRepository) {
        return (args) -> {
            customerRepository.save(new Customer("Mark", "Smith"));
            customerRepository.save(new Customer("Jennifer", "Bates"));
            customerRepository.save(new Customer("Diana", "Hewitt"));
            customerRepository.save(new Customer("Albert", "Freeman"));

            Species cat = speciesRepository.save(new Species("Cat"));
            Species dog = speciesRepository.save(new Species("Dog"));

            petRepository.save(new Pet("Teddy", dog));
            petRepository.save(new Pet("Baxter", dog));
            petRepository.save(new Pet("Whiskers", cat));
            petRepository.save(new Pet("Bella", cat));
        };
    }
}
