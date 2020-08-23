package com.net128.application.vaadin.petstore.util;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidationExceptionFormatter {
    public static String format(ConstraintViolationException e) {
        Map<String, String> errors = new HashMap<>();
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            errors.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
        }
        return errors.keySet().stream()
            .map(key -> key + ": " + errors.get(key))
            .collect(Collectors.joining("\n"));
    }
}
