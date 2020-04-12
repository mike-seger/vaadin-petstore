package com.net128.application.vaadin.petstore.repo;

import java.util.List;

import com.net128.application.vaadin.petstore.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByLastNameStartsWithIgnoreCase(String lastName);
}
