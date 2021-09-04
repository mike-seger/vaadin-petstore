package com.net128.application.vaadin.petstore.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@ToString
@Getter @Setter
@Table(uniqueConstraints= {
    //FIXME This doesn't kick in in H2!!
    @UniqueConstraint(columnNames = {Purchase.petID})
})
public class Purchase extends Identifiable{
    final static String petID = "pet_id";
    @NotNull
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name=petID)
    @NotNull
    private Pet pet;

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @NotNull
    private Customer customer;

    @PrePersist
    public void prePersist() {
        date = LocalDateTime.now();
    }
}
