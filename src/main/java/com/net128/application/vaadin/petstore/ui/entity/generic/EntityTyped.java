package com.net128.application.vaadin.petstore.ui.entity.generic;

import java.lang.reflect.ParameterizedType;

public interface EntityTyped<T> {
    @SuppressWarnings ("unchecked")
    default Class<T> getTypeParameterClass() {
        final var type = getClass().getGenericSuperclass();
        final var paramType = (ParameterizedType) type;
        return (Class<T>) paramType.getActualTypeArguments()[0];
    }

    default String getTypeName() {
        return getTypeParameterClass().getSimpleName();
    }
}
