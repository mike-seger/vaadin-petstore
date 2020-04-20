package com.net128.application.vaadin.petstore.model;

import com.net128.application.vaadin.petstore.repo.EntityChangeBroadCaster;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EntityListeners(EntityChangeBroadCaster.class)
public abstract class Identifiable {
    @Id
    @GeneratedValue
    private long id = -1;
}
