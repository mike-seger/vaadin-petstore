package com.net128.application.vaadin.petstore.ui;

import com.net128.application.vaadin.petstore.model.User;
import com.net128.application.vaadin.petstore.repo.UserRepository;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class UserEditor extends VerticalLayout implements KeyNotifier {

    private final UserRepository repository;

    private User user;

    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");

    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<User> binder = new Binder<>(User.class);
    private ChangeHandler changeHandler;

    @Autowired
    public UserEditor(UserRepository repository) {
        this.repository = repository;

        add(firstName, lastName, actions);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> edituser(user));
        setVisible(false);
    }

    void delete() {
        repository.delete(user);
        changeHandler.onChange();
    }

    void save() {
        repository.save(user);
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void edituser(User c) {
        if (c == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = c.getId() != null;
        if (persisted) {
            user = repository.findById(c.getId()).get();
        } else {
            user = c;
        }

        cancel.setVisible(persisted);
        binder.setBean(user);
        setVisible(true);
        firstName.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }
}
