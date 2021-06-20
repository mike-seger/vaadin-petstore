package com.net128.application.vaadin.petstore.model;

import lombok.*;

import javax.persistence.Column;
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
public class Country extends Identifiable {
    @NotBlank
    private String name;
    @NotBlank
    @Column(length=2)
    private String iso2;
    @NotBlank
    @Column(length=3, unique = true)
    private String iso3;
    @NotBlank
    private String phoneCode;
    @NotBlank
    private String emoji;
}
