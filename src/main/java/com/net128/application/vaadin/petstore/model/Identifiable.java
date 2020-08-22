package com.net128.application.vaadin.petstore.model;

import com.net128.application.vaadin.petstore.repo.EntityChangeBroadCaster;
import lombok.Data;

import javax.persistence.*;

@MappedSuperclass
@Data
@EntityListeners(EntityChangeBroadCaster.class)
public abstract class Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = -1L;
}
