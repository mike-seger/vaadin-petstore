package com.net128.application.vaadin.petstore.ui;

import com.net128.application.vaadin.petstore.model.User;
import com.net128.application.vaadin.petstore.repo.UserRepository;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.KeyNotifier;
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
public class MainView extends VerticalLayout implements KeyNotifier {

    private final UserRepository userRepository;
    final Grid<User> grid;

    public MainView(UserRepository userRepository, UserEditor userEditor) {
        this.userRepository = userRepository;

        setMargin(false);
        //setSpacing(false);
        setPadding(false);

        final TextField userNameFilter = new TextField();
        userNameFilter.setPlaceholder("Find By Name");
        userNameFilter.addFocusShortcut(Key.KEY_F, KeyModifier.ALT);
        userNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        userNameFilter.addValueChangeListener(e -> listUsers(e.getValue()));

        final Button newUserButton = new Button("New User", VaadinIcon.PLUS.create());
        newUserButton.addClickShortcut(Key.KEY_N, KeyModifier.ALT);
        newUserButton.addClickListener(e -> userEditor.newUser());

        final Button toggleButton = new Button("Toggle dark theme", click -> toggleDarkTheme());
        toggleButton.addClickShortcut(Key.KEY_T, KeyModifier.ALT);

        this.grid = new Grid<>(User.class);
  //      final MultiSelectionModel multiselectionModel = (MultiSelectionModel) grid.getSelectionModel();
        grid.setHeightFull();
        grid.setWidthFull();
        grid.setColumns("id", "firstName", "lastName");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);
        grid.setColumnReorderingAllowed(true);
//        ((GridMultiSelectionModel<?>) grid
//            .setSelectionMode(Grid.SelectionMode.SINGLE))
//            .setSelectionColumnFrozen(true);

        UI.getCurrent().addShortcutListener(u -> userEditor.newUser(), Key.KEY_N,
            KeyModifier.CONTROL, KeyModifier.ALT);

        addKeyDownListener(Key.ARROW_UP, event -> System.out.println("ARROW_UP: "+
                event.getSource().toString() + " -> "+
                event.getSource().getId()));

        addKeyDownListener(Key.ARROW_DOWN, event -> System.out.println("ARROW_DOWN: "+event.getSource().getId()));

        //this.addClickShortcut(Key.KEY_L, KeyModifier.ALT);
        grid.asSingleSelect().addValueChangeListener(
            e -> userEditor.edituser(e.getValue()));

        //grid.addItemClickListener();
        HorizontalLayout actionBar = new HorizontalLayout(userNameFilter, newUserButton, toggleButton);
        HorizontalLayout masterDetail = new HorizontalLayout(grid, userEditor);
        masterDetail.setHeightFull();
        masterDetail.setWidthFull();

        userEditor.setWidth("400px");
        userEditor.setChangeHandler(() -> {
            userEditor.setVisible(false);
            listUsers(userNameFilter.getValue());
        });

    //    FastNavigation nav = new FastNavigation<User>(grid, false, true);

        setSizeFull();

        add(new AppBar("Pet Store"));
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
            grid.setItems(userRepository.findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrderById(filterText, filterText));
        }
    }
}
