package com.net128.application.vaadin.petstore.model;

import lombok.*;

import javax.persistence.Entity;

@Entity
@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends Identifiable {
    @lombok.NonNull
    private String firstName;

    @lombok.NonNull
    private String lastName;
}
