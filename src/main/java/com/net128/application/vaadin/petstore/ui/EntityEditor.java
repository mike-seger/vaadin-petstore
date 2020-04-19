package com.net128.application.vaadin.petstore.ui;

import com.net128.application.vaadin.petstore.model.Identifiable;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@SpringComponent
@UIScope
public class EntityEditor<T extends Identifiable> extends VerticalLayout implements KeyNotifier {

    private final JpaRepository<T, Long> repository;

    private T entity;

    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    final private Binder<T> binder;
    private ChangeHandler changeHandler;

    @Autowired
    public EntityEditor(JpaRepository<T, Long> repository) {
        this.repository = repository;
        binder = new Binder<>(getTypeParameterClass());
    }

    protected void layout() {
        add(actions);
        binder.bindInstanceFields(this);
        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> {
            edit(entity);
            setVisible(false);
        });
        setVisible(false);
    }

    void delete() {
        repository.delete(entity);
        changeHandler.onChange();
    }

    void save() {
        repository.save(entity);
        changeHandler.onChange();
    }

    void editNew() {
        edit(getNewT());
    }

    void edit(T currentEntity) {
        if (currentEntity == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = currentEntity.getId() != -1;
        if (persisted) {
            if(repository.findById(currentEntity.getId()).isPresent()) {
                entity = repository.findById(currentEntity.getId()).get();
            }
        } else {
            entity = currentEntity;
        }

        cancel.setVisible(persisted);
        binder.setBean(entity);
        setVisible(true);
    }

    public interface ChangeHandler {
        void onChange();
    }

    @SuppressWarnings ("unchecked")
    protected Class<T> getTypeParameterClass() {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) type;
        return (Class<T>) paramType.getActualTypeArguments()[0];
    }

    private T getNewT() {
        try {
            return getTypeParameterClass().newInstance();
        } catch (InstantiationException|IllegalAccessException e) {
            throw new RuntimeException("Failed to create new entity", e);
        }
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }
}
