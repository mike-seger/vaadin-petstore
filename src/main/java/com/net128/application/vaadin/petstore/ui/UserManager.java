package com.net128.application.vaadin.petstore.ui;

import com.net128.application.vaadin.petstore.model.User;
import com.net128.application.vaadin.petstore.repo.UserRepository;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.util.StringUtils;

import java.util.List;

@SpringComponent
@UIScope
public class UserManager extends EntityManager<User> {

    private final UserRepository userRepository;
    private TextField userNameFilter;

    public UserManager(UserRepository userRepository, UserEditor userEditor) {
        super(userEditor);
        this.userRepository = userRepository;
        layout();
    }

    public void setupGrid(Grid<User> grid) {
        grid.setColumns("firstName", "lastName");
    }

    public HorizontalLayout createActionBar(EntityEditor<User> userEditor) {
        userNameFilter = new TextField();
        userNameFilter.setPlaceholder("Find in any name...");
        userNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        userNameFilter.addValueChangeListener(e -> setGridData(listEntities()));
        final Button newUserButton = new Button("New User...", VaadinIcon.PLUS.create());
        newUserButton.addClickListener(e -> userEditor.editNew());
        return new HorizontalLayout(userNameFilter, newUserButton);
    }

    public List<User> listEntities() {
        List<User> users;
        String filterText = userNameFilter.getValue();
        if (StringUtils.isEmpty(filterText)) {
            users = userRepository.findAll();
        } else {
            users = userRepository.findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrderById(filterText, filterText);
        }
        return users;
    }
}
