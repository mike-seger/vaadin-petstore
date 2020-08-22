package com.net128.application.vaadin.petstore.ui.entity;

import com.net128.application.vaadin.petstore.model.Identifiable;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@SpringComponent
@UIScope
public class EntityEditor<T extends Identifiable> extends VerticalLayout implements EntityChangeAware, KeyNotifier {

    final private JpaRepository<T, Long> repository;

    private T entity;

    final private Binder<T> binder;

    private Button cancel;

    private EntityChangedHandler entityChangedHandler;

    public EntityEditor(JpaRepository<T, Long> repository) {
        this.repository = repository;
        binder = new Binder<>(getTypeParameterClass());
    }

    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        layout();
    }

    public void layout() {
        cancel = new Button("Cancel");
        final Button save = new Button("Save", VaadinIcon.CHECK.create());
        final Button delete = new Button("Delete", VaadinIcon.TRASH.create());
        final HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

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
        entityChanged(null);
    }

    void delete() {
        repository.delete(entity);
        setVisible(false);
    }

    void save() {
        entity = repository.save(entity);
        setVisible(false);
    }

    public void editNew() {
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

    public interface EntityChangedHandler {
        void entityChanged(Identifiable entity);
    }

    public void setEntityChangedHandler(EntityChangedHandler entityChangedHandler) {
        this.entityChangedHandler = entityChangedHandler;
    }

    @Override
    public void entityChanged(Identifiable entity) {
        if(entityChangedHandler != null) {
            entityChangedHandler.entityChanged(entity);
        }
    }

    @SuppressWarnings ("unchecked")
    protected Class<T> getTypeParameterClass() {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) type;
        return (Class<T>) paramType.getActualTypeArguments()[0];
    }

    private T getNewT() {
        try {
            return getTypeParameterClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Failed to create new entity", e);
        }
    }
}
