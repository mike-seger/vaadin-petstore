package com.net128.application.vaadin.petstore.ui;

import com.net128.application.vaadin.petstore.model.User;
import com.net128.application.vaadin.petstore.repo.UserRepository;
import org.springframework.util.StringUtils;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {

    private final UserRepository userRepository;

    private final UserEditor userEditor;

    final Grid<User> grid;

    final TextField filter;

    private final Button addNewBtn;

    public MainView(UserRepository repo, UserEditor userEditor) {
        this.userRepository = repo;
        this.userEditor = userEditor;
        this.grid = new Grid<>(User.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("New user", VaadinIcon.PLUS.create());

        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        add(actions, grid, userEditor);

        grid.setHeight("200px");
        grid.setColumns("id", "firstName", "lastName");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        filter.setPlaceholder("Filter by last name");

        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listusers(e.getValue()));

        grid.asSingleSelect().addValueChangeListener(e -> {
            userEditor.edituser(e.getValue());
        });

        addNewBtn.addClickListener(e -> userEditor.edituser(new User("", "")));

        userEditor.setChangeHandler(() -> {
            userEditor.setVisible(false);
            listusers(filter.getValue());
        });

        listusers(null);
    }

    void listusers(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(userRepository.findAll());
        } else {
            grid.setItems(userRepository.findByLastNameStartsWithIgnoreCase(filterText));
        }
    }
}
