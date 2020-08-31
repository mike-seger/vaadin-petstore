package com.net128.application.vaadin.petstore.ui.entity.generic;

import com.net128.application.vaadin.petstore.model.Identifiable;

public interface EntityChangeAware {
    void entityChanged(Identifiable entity);
}
