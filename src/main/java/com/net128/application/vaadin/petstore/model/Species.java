package com.net128.application.vaadin.petstore.model;

import lombok.*;

import javax.persistence.Entity;

@Entity
@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Species extends Identifiable {
    private String name= "";
}
