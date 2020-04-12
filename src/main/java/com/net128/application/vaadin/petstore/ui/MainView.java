package com.net128.application.vaadin.petstore.ui;

import com.net128.application.vaadin.petstore.model.User;
import com.net128.application.vaadin.petstore.repo.UserRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.theme.lumo.Lumo;
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
    final Grid<User> grid;

    public MainView(UserRepository userRepository, UserEditor userEditor) {
        this.userRepository = userRepository;
        this.grid = new Grid<>(User.class);

        final TextField userNameFilter = new TextField();
        userNameFilter.setPlaceholder("Filter by last name");
        userNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        userNameFilter.addValueChangeListener(e -> listUsers(e.getValue()));

        final Button newUserButton = new Button("New User", VaadinIcon.PLUS.create());
        newUserButton.addClickListener(e -> userEditor.edituser(new User("", "")));

        final Button toggleButton = new Button("Toggle dark theme", click -> toggleDarkTheme());

        HorizontalLayout actionBar = new HorizontalLayout(userNameFilter, newUserButton, toggleButton);
        HorizontalLayout masterDetail = new HorizontalLayout(grid, userEditor);
        masterDetail.setHeightFull();
        masterDetail.setWidthFull();

        grid.setHeightFull();
        grid.setWidthFull();
        grid.setColumns("id", "firstName", "lastName");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);
        grid.asSingleSelect().addValueChangeListener(e -> userEditor.edituser(e.getValue()));

        userEditor.setWidth("400px");
        userEditor.setChangeHandler(() -> {
            userEditor.setVisible(false);
            listUsers(userNameFilter.getValue());
        });

        setSizeFull();
        add(actionBar, masterDetail);

        listUsers(null);
    }

    void toggleDarkTheme() {
        ThemeList themeList = UI.getCurrent().getElement().getThemeList();
        if (themeList.contains(Lumo.DARK)) { //
            themeList.remove(Lumo.DARK);
        } else {
            themeList.add(Lumo.DARK);
        }
    }

    void listUsers(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(userRepository.findAll());
        } else {
            grid.setItems(userRepository.findByLastNameStartsWithIgnoreCase(filterText));
        }
    }
}
