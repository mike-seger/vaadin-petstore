package com.net128.application.vaadin.petstore.ui;

import com.net128.application.vaadin.petstore.ui.entity.manager.CustomerManager;
import com.net128.application.vaadin.petstore.ui.entity.manager.PetManager;
import com.net128.application.vaadin.petstore.ui.entity.manager.SpeciesManager;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.themes.ValoTheme;

@Route
@Push
@CssImport("./styles/components/main-view.css")
@JsModule("@vaadin/vaadin-lumo-styles/presets/compact.js")
/*
https://vaadin.com/learn/tutorials/themes-and-styling-in-vaadin
 */
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class MainView extends VerticalLayout implements KeyNotifier {

    public MainView(CustomerManager customerManager,
                    PetManager petManager,
                    SpeciesManager speciesManager) {
        //final Button toggleButton = new Button("Toggle dark theme", click -> toggleDarkTheme());
        Button toggleButton = new Button(VaadinIcon.MENU.create(), click -> toggleDarkTheme());
        //square.setIcon(VaadinIcon.MENU.create());
        //square.addStyleName(ValoTheme.BUTTON_ICON_ONLY + " " + ValoTheme.BUTTON_SMALL + " square");

        toggleButton.addClickShortcut(Key.KEY_T, KeyModifier.ALT);
        setMargin(false);
        setSpacing(false);
        setPadding(false);

        AppBar appBar = new AppBar("Pet Store", toggleButton);
        add(appBar);

        add(new TabPageManager(
            new TabPage("Pets", petManager),
            new TabPage("Species", speciesManager),
            new TabPage("Customers", customerManager),
            new TabPage("Purchases", new Div(new Text("Purchases not yet implemented")))
        ));
    }

    void toggleDarkTheme() {
        ThemeList themeList = UI.getCurrent().getElement().getThemeList();
        if (themeList.contains(Lumo.LIGHT)) {
            themeList.remove(Lumo.LIGHT);
            themeList.add(Lumo.DARK);
        } else {
            themeList.add(Lumo.LIGHT);
            themeList.remove(Lumo.DARK);
        }
    }
}
