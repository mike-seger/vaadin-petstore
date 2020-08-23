package com.net128.application.vaadin.petstore.model;

import com.net128.application.vaadin.petstore.repo.EntityChangeBroadcaster;
import lombok.Data;

import javax.persistence.*;

@MappedSuperclass
@Data
@EntityListeners(EntityChangeBroadcaster.class)
public abstract class Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
