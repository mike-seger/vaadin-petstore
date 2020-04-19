package com.net128.application.vaadin.petstore.repo;

import com.net128.application.vaadin.petstore.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
