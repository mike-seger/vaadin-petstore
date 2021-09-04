package com.net128.application.vaadin.petstore.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Preferences extends Identifiable {
    @NotNull
    private Boolean darkMode;
    
    private String currentTab;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private User user;

    public void copy(Preferences preferences) {
        this.setDarkMode(preferences.getDarkMode());
    }
}
