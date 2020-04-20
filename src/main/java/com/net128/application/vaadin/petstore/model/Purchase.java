package com.net128.application.vaadin.petstore.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@ToString
@Getter @Setter
public class Purchase extends Identifiable{
    private LocalDateTime date;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Pet pet;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @PrePersist
    public void prePersist() {
        date = LocalDateTime.now();
    }
}
