package com.net128.application.vaadin.petstore.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
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
