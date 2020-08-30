package com.net128.application.vaadin.petstore.util;

import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ExceptionFormatter {
    public static String format(Exception e) {
        if(e instanceof ConstraintViolationException) {
            return formatConstraintViolationException((ConstraintViolationException)e);
        }
        String message = ExceptionUtils.getRootCause(e).getMessage();
        message = message
            .replaceAll("(?m)^ *(Detail:)", "$1");
        return message;
    }

    public static String formatConstraintViolationException(ConstraintViolationException e) {
        Map<String, String> errors = new HashMap<>();
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            errors.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
        }
        String errorString = errors.keySet().stream()
            .map(key -> key + ": " + errors.get(key))
            .collect(Collectors.joining("\n"))
            //.replaceAll("\\n", "\n")
            ;
        return errorString;
    }
}
