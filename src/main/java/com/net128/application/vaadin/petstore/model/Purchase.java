package com.net128.application.vaadin.petstore.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@ToString
@Getter @Setter
@Table(uniqueConstraints= {
    @UniqueConstraint(columnNames = {"pet_id"})
})
public class Purchase extends Identifiable{
    @NotNull
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="pet_id")
    @NotNull
    private Pet pet;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private Customer customer;

    @PrePersist
    public void prePersist() {
        date = LocalDateTime.now();
    }
}
