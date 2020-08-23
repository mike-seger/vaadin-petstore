package com.net128.application.vaadin.petstore.ui.entity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public interface EntityTyped<T> {
    @SuppressWarnings ("unchecked")
    default Class<T> getTypeParameterClass() {
        final Type type = getClass().getGenericSuperclass();
        final ParameterizedType paramType = (ParameterizedType) type;
        return (Class<T>) paramType.getActualTypeArguments()[0];
    }

    default String getTypeName() {
        return getTypeParameterClass().getSimpleName();
    }
}
