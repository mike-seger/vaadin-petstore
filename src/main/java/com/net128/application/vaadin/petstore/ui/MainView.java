package com.net128.application.vaadin.petstore.ui;

import com.net128.application.vaadin.petstore.repo.PetRepository;
import com.net128.application.vaadin.petstore.repo.UserRepository;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;

@Route
public class MainView extends VerticalLayout implements KeyNotifier {

    public MainView(
        UserManager userManager, PetManager petManager) {

        final Button toggleButton = new Button("Toggle dark theme", click -> toggleDarkTheme());
        toggleButton.addClickShortcut(Key.KEY_T, KeyModifier.ALT);
        setMargin(false);
        setSpacing(false);
        setPadding(false);

        AppBar appBar = new AppBar("Pet Store");
        add(appBar);
        appBar.add(toggleButton);

        add(new TabPageManager(
            new TabPage("Users", userManager),//new UserManager(userRepository, userEditor)),
            new TabPage("Pets", petManager),//new PetManager(petRepository, petEditor)),
            new TabPage("Species", new Div(new Text("Species not yet implemented"))),
            new TabPage("Purchases", new Div(new Text("Purchases not yet implemented")))
        ));
    }

    void toggleDarkTheme() {
        ThemeList themeList = UI.getCurrent().getElement().getThemeList();
        if (themeList.contains(Lumo.DARK)) {
            themeList.remove(Lumo.DARK);
        } else {
            themeList.add(Lumo.DARK);
        }
    }
}
