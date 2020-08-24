package com.net128.application.vaadin.petstore.ui.entity;

import com.net128.application.vaadin.petstore.model.Identifiable;
import com.net128.application.vaadin.petstore.util.ExceptionFormatter;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.InvocationTargetException;

@SpringComponent
@UIScope
@Slf4j
public class EntityEditor<T extends Identifiable> extends VerticalLayout implements EntityChangeAware, KeyNotifier, EntityTyped<T> {

    final private JpaRepository<T, Long> repository;

    private T entity;

    final private Binder<T> binder;

    private Button delete;

    private EntityChangedHandler entityChangedHandler;

    private Text title;
    private Label errorMessage;

    public EntityEditor(JpaRepository<T, Long> repository) {
        this.repository = repository;
        binder = new Binder<>(getTypeParameterClass());
    }

    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        layout();

        title = new Text("");
        addComponentAtIndex(0, title);
    }

    public void layout() {
        final Button cancel = new Button("Cancel");
        final Button save = new Button("Save", VaadinIcon.CHECK.create());
        delete = new Button("Delete", VaadinIcon.TRASH.create());
        final HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

        add(actions);

        errorMessage = new Label();
        errorMessage.getElement().getStyle().set("color", "hsl(3, 92%, 53%)");
        errorMessage.getElement().getStyle().set("white-space", "pre-wrap");
        add(errorMessage);

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

    protected void delete() {
        repository.delete(entity);
        setVisible(false);
    }

    protected void save() {
        try {
            entity = repository.save(entity);
            setVisible(false);
        } catch(Exception e) {
            log.info("Error saving entity", e);
            errorMessage.setVisible(true);
            errorMessage.setText(ExceptionFormatter.format(e));
        }
    }

    public void editNew() {
        edit(getNewT());
    }

    void edit(T currentEntity) {
        errorMessage.setVisible(false);
        if (currentEntity == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = currentEntity.getId() != null;
        if (persisted) {
            title.setText("Edit "+getTypeName());
            if(repository.findById(currentEntity.getId()).isPresent()) {
                entity = repository.findById(currentEntity.getId()).get();
            }
        } else {
            title.setText("New "+getTypeName());
            entity = currentEntity;
        }

        delete.setVisible(persisted);
        binder.setBean(entity);
        setVisible(true);
    }

    public T getCurrentEntity() {
        return entity;
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

    private T getNewT() {
        try {
            return getTypeParameterClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Failed to create new entity", e);
        }
    }
}
