package com.net128.application.vaadin.petstore.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Purchase extends Identifiable{
    private LocalDateTime date;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    private Pet pet;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    private User user;

    @PrePersist
    public void prePersist() {
        date = LocalDateTime.now();
    }
}
