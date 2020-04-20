package com.net128.application.vaadin.petstore.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pet extends Identifiable {
    private String name = "";

    @ManyToOne(fetch = FetchType.EAGER)
    private Species species;
}
