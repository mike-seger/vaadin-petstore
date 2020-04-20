package com.net128.application.vaadin.petstore.ui;

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
        UserManager userManager, PetManager petManager, SpeciesManager speciesManager) {

        final Button toggleButton = new Button("Toggle dark theme", click -> toggleDarkTheme());
        toggleButton.addClickShortcut(Key.KEY_T, KeyModifier.ALT);
        setMargin(false);
        setSpacing(false);
        setPadding(false);

        AppBar appBar = new AppBar("Pet Store");
        add(appBar);
        appBar.add(toggleButton);

        add(new TabPageManager(
            new TabPage("Users", userManager),
            new TabPage("Pets", petManager),
            new TabPage("Species", speciesManager),
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
