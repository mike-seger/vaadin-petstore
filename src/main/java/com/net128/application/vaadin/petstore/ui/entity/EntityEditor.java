package com.net128.application.vaadin.petstore.ui.entity;

import com.net128.application.vaadin.petstore.model.Identifiable;
import com.net128.application.vaadin.petstore.util.ExceptionFormatter;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.ConstraintViolationException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

@SpringComponent
@UIScope
@Slf4j
public class EntityEditor<T extends Identifiable> extends VerticalLayout implements EntityChangeAware, KeyNotifier, EntityTyped<T> {

    final private JpaRepository<T, Long> repository;

    private T entity;

    //final private Binder<T> binder;
    final private BeanValidationBinder<T> validationBinder;

    private Button delete;

    private EntityChangedHandler entityChangedHandler;

    final private Text title = new Text("");
    private Label errorMessage;
    final private FormLayout editFields = new FormLayout();

    public EntityEditor(JpaRepository<T, Long> repository) {
        this.repository = repository;
        //binder = new Binder<>(getTypeParameterClass());
        validationBinder = new BeanValidationBinder<>(getTypeParameterClass());
    }

    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        layout();
    }

    @Override
    public void add(Component... components) {
        Arrays.stream(components).forEach(c ->
        {
            editFields.addFormItem(c, c.getElement().getProperty("label"));
            c.getElement().setProperty("label", null);
        });
    }

    public void layout() {
        editFields.setResponsiveSteps(
            new FormLayout.ResponsiveStep("25em", 1),
            new FormLayout.ResponsiveStep("32em", 2),
            new FormLayout.ResponsiveStep("40em", 3));

        final Button cancel = new Button("Cancel");
        final Button save = new Button("Save", VaadinIcon.CHECK.create());
        delete = new Button("Delete", VaadinIcon.TRASH.create());
        final HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

        super.add(title);
        super.add(editFields);
        super.add(actions);

        errorMessage = new Label();
        errorMessage.getElement().getStyle().set("color", "hsl(3, 92%, 53%)");
        errorMessage.getElement().getStyle().set("white-space", "pre-wrap");        errorMessage.getElement().getStyle().set("white-space", "pre-wrap");
        errorMessage.getElement().getStyle().set("font-size", "14px");
        super.add(errorMessage);

        //binder.bindInstanceFields(this);
        validationBinder.bindInstanceFields(this);
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
        validationBinder.setBean(null);
    }

    protected void save() {
        try {
            validationBinder.setValidatorsDisabled(false);
            if(validationBinder.validate().isOk()) {
                entity = repository.save(entity);
                setVisible(false);
            }
        } catch(Exception e) {
            log.info("Error saving entity", e);
            if(! (e instanceof ConstraintViolationException)) {
                errorMessage.setVisible(true);
                errorMessage.setText(ExceptionFormatter.format(e));
            }
        }
    }

    public void editNew() {
        edit(entity);
        setVisible(false);
        //validationBinder.setV
        //validationBinder.setBean(null);
        //setRequiredIndicatorVisible(false);
        //validationBinder.is
        //validationBinder.getFields().forEach(f -> validationBinder.getBinding(f.).setValidatorsDisabled(false);
        edit(getNewT());
    }

    void edit(T currentEntity) {
        //validationBinder
        entity = null;
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
            validationBinder.setValidatorsDisabled(false);
        } else {
            title.setText("New "+getTypeName());
            entity = currentEntity;
            validationBinder.setValidatorsDisabled(true);
        }

        validationBinder.setBean(entity);

        delete.setVisible(persisted);
        //binder.setBean(entity);

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
