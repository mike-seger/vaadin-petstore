package com.net128.application.vaadin.petstore.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
public abstract class Identifiable {
    @Id
    @GeneratedValue
    private long id = -1;
}
