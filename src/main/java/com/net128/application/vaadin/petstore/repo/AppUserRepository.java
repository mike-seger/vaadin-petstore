package com.net128.application.vaadin.petstore.repo;

import com.net128.application.vaadin.petstore.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {}