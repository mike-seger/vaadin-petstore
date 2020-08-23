package com.net128.application.vaadin.petstore.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pet extends Identifiable {
    @NotBlank
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private Species species;

    @NotNull
    @Min(value = 1)
    private Double price;
}
