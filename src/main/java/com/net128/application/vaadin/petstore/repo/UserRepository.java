package com.net128.application.vaadin.petstore.repo;

import java.util.List;

import com.net128.application.vaadin.petstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByLastNameStartsWithIgnoreCase(String lastName);
}
