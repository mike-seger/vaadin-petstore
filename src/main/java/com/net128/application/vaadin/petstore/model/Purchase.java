package com.net128.application.vaadin.petstore.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@ToString
@Getter @Setter
public class Purchase extends Identifiable{
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.EAGER)
    private Pet pet;

    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;

    @PrePersist
    public void prePersist() {
        date = LocalDateTime.now();
    }
}
