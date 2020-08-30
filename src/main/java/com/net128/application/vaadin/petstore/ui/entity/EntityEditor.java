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
public class EntityEditor<T extends Identifiable> extends VerticalLayout implements EntityChangeAware, KeyNotifier, EntityTyped<T>, HasValue.ValueChangeListener<HasValue.ValueChangeEvent<?>> {

    final private JpaRepository<T, Long> repository;

    private T entity;

    //FIXME this flag shouldn't be required - validationBinder.hasChanges returns false in several cases though
    private boolean dirty;

    final private BeanValidationBinder<T> validationBinder;

    private Button delete;

    private EntityChangedHandler entityChangedHandler;

    final private Text title = new Text("");
    private Label errorMessage;
    final private FormLayout editFields = new FormLayout();

    public EntityEditor(JpaRepository<T, Long> repository) {
        this.repository = repository;
        validationBinder = new BeanValidationBinder<>(getTypeParameterClass());
    }

    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        layout();
    }

    @Override
    public void add(Component... components) {
        Arrays.stream(components).forEach(c -> {
            String label = c.getElement().getProperty("label");
            label = label==null?
                formatFieldNameAsLabel(getFieldName(c, this)) : label;
            editFields.addFormItem(c, new Label(label));
            c.getElement().setProperty("label", null);
        });
    }

    public void layout() {
        editFields.setResponsiveSteps(
            new FormLayout.ResponsiveStep("18em", 1),
            new FormLayout.ResponsiveStep("36em", 2),
            new FormLayout.ResponsiveStep("54em", 3));

        final Button cancel = new Button("Cancel");
        final Button save = new Button("Save", VaadinIcon.CHECK.create());
        delete = new Button("Delete", VaadinIcon.TRASH.create());
        redButton(delete);
        final HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

        validationBinder.getFields().forEach(f -> {
            f.setRequiredIndicatorVisible(false);
        });

        super.add(title);
        super.add(editFields);
        super.add(actions);

        errorMessage = customMultilineLabel(null);
        super.add(errorMessage);

        validationBinder.bindInstanceFields(this);
        validationBinder.addValueChangeListener(this);

        setSpacing(true);

        save.addClickShortcut(Key.ENTER);
        save.getElement().getThemeList().add("primary");
        //delete.getElement().getThemeList().add("error");

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> cancel());
        setVisible(false);
        entityChanged(null);
    }

    private void redButton(Button button) {
        button.getElement().getStyle().set("color", "white");
        button.getElement().getStyle().set("background", "hsl(0, 68%, 62%)");
    }

    private Label customMultilineLabel(String text) {
        Label label = new Label(text);
        label.getElement().getStyle().set("color", "hsl(3, 92%, 53%)");
        label.getElement().getStyle().set("white-space", "pre-wrap");
        label.getElement().getStyle().set("font-size", "14px");
        return label;
    }

    private static String formatFieldNameAsLabel(String fieldName) {
        if(fieldName==null) {
            return null;
        }
        String name = fieldName.replaceAll("([a-z])([A-Z]+)", "$1 $2");
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    private static String getFieldName(Object fieldObject, Object parent) {
        java.lang.reflect.Field[] allFields = parent.getClass().getDeclaredFields();
        for (java.lang.reflect.Field field : allFields) {
            field.setAccessible(true);
            Object currentFieldObject;
            try {
                currentFieldObject = field.get(parent);
            } catch (Exception e) {
                return null;
            }
            boolean isWantedField = fieldObject.equals(currentFieldObject);
            if (isWantedField) {
                return field.getName();
            }
        }
        return null;
    }

    protected void cancel() {
        entity = null;
        validationBinder.removeBean();
        setVisible(false);
        validationBinder.removeBean();
        validationBinder.validate();
        validationBinder.readBean(null);
        validationBinder.writeBeanAsDraft(null);
        entityChanged(null);
        dirty = false;
    }

    protected void delete() {
        repository.delete(entity);
        validationBinder.removeBean();
        setVisible(false);
        dirty = false;
    }

    protected void save() {
        try {
            if(validationBinder.validate().isOk()) {
                entity = repository.save(entity);
                validationBinder.writeBean(entity);
                validationBinder.removeBean();
                dirty = false;
                entityChanged(null);
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
        if(validationBinder.hasChanges() || dirty) {
            showDiscardMessage();
            return;
        }
        T currentEntity = getNewT();
        validationBinder.setBean(currentEntity);
        edit(currentEntity);
    }

    public void showDiscardMessage() {
        errorMessage.setVisible(true);
        errorMessage.setText("Press 'Save' to save changes\nor 'Cancel' to discard current edits.");
    }

    void edit(T currentEntity) {
        if (currentEntity == null) {
            setVisible(false);
            return;
        }

        setVisible(true);
        if(validationBinder.hasChanges() || dirty) {
            showDiscardMessage();
            return;
        }

        entity = null;
        errorMessage.setVisible(false);

        final boolean persisted = currentEntity.getId() != null;
        if (persisted) {
            title.setText("Edit "+getTypeName());
            if(repository.findById(currentEntity.getId()).isPresent()) {
                entity = repository.findById(currentEntity.getId()).get();
            }
            validationBinder.setBean(entity);
        } else {
            entity = currentEntity;
            title.setText("New "+getTypeName());
        }

        delete.setVisible(persisted);
    }

    public T getCurrentEntity() {
        return entity;
    }

    @Override
    public void valueChanged(HasValue.ValueChangeEvent<?> valueChangeEvent) {
        log.info("Value Change Event: {}", valueChangeEvent.getValue());
        dirty = entity!=null;
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
