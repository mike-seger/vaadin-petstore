package com.net128.application.vaadin.petstore.ui;

import com.net128.application.vaadin.petstore.model.User;
import com.net128.application.vaadin.petstore.repo.UserRepository;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.springframework.util.StringUtils;

import java.util.List;

public class UserManager extends WorkingArea {

    private final UserRepository userRepository;
    private final Grid<User> grid;

    public UserManager(UserRepository userRepository, UserEditor userEditor) {
        this.userRepository = userRepository;

        setSizeFull();

        final TextField userNameFilter = new TextField();
        userNameFilter.setPlaceholder("Find By Name");
        userNameFilter.addFocusShortcut(Key.KEY_F, KeyModifier.ALT);
        userNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        userNameFilter.addValueChangeListener(e -> listUsers(e.getValue()));

        final Button newUserButton = new Button("New User", VaadinIcon.PLUS.create());
        newUserButton.addClickShortcut(Key.KEY_N, KeyModifier.ALT);
        newUserButton.addClickListener(e -> userEditor.newUser());

        grid = new Grid<>(User.class);
        grid.setSizeFull();
        //FIXME this is a hack, but otherwise the grid won't show
        grid.setHeight("400px");
        grid.setColumns("id", "firstName", "lastName");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);
        grid.setColumnReorderingAllowed(true);

        UI.getCurrent().addShortcutListener(u -> userEditor.newUser(), Key.KEY_N,
                KeyModifier.CONTROL, KeyModifier.ALT);

        grid.asSingleSelect().addValueChangeListener(
                e -> userEditor.edituser(e.getValue()));

        HorizontalLayout actionBar = new HorizontalLayout(userNameFilter, newUserButton);
        HorizontalLayout masterDetail = new HorizontalLayout(grid, userEditor);
        masterDetail.setSizeFull();

        userEditor.setWidth("400px");
        userEditor.setChangeHandler(() -> {
            userEditor.setVisible(false);
            listUsers(userNameFilter.getValue());
        });
        add(actionBar, masterDetail);
        listUsers(null);
    }

    void listUsers(String filterText) {
        List<User> users;
        if (StringUtils.isEmpty(filterText)) {
            users = userRepository.findAll();
        } else {
            users = userRepository.findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrderById(filterText, filterText);
        }
        grid.setItems(users);
    }
}
