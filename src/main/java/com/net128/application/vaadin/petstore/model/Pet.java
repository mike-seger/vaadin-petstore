package com.net128.application.vaadin.petstore.model;

import lombok.*;

import javax.persistence.*;

@Entity
@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pet extends Identifiable {
    @NonNull
    private String name = "";

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Species species;
}
