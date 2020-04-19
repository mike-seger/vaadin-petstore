package com.net128.application.vaadin.petstore.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet extends Identifiable {
    @NonNull
    //@Column(nullable = false)
    private String name = "";

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    private Species species;
}
