package com.net128.application.vaadin.petstore.ui;

import com.net128.application.vaadin.petstore.model.Preferences;
import com.net128.application.vaadin.petstore.repo.PreferencesRepository;
import com.net128.application.vaadin.petstore.ui.entity.managers.CustomerManager;
import com.net128.application.vaadin.petstore.ui.entity.managers.PetManager;
import com.net128.application.vaadin.petstore.ui.entity.managers.PurchaseManager;
import com.net128.application.vaadin.petstore.ui.entity.managers.SpeciesManager;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Route
@CssImport("./styles/components/main-view.css")
@JsModule("@vaadin/vaadin-lumo-styles/presets/compact.js")
@Slf4j
public class MainView extends FlexLayout implements KeyNotifier {
    private final PreferencesRepository preferencesRepository;

    public MainView(CustomerManager customerManager,
            PetManager petManager,
            SpeciesManager speciesManager,
            PurchaseManager purchaseManager,
            PreferencesRepository preferencesRepository) {

        this.preferencesRepository = preferencesRepository;
        setClassName("main-view");
        Button toggleButton = new Button(VaadinIcon.MENU.create(), click -> toggleDarkTheme());

        toggleButton.addClickShortcut(Key.KEY_T, KeyModifier.ALT);

        AppBar appBar = new AppBar("Pet Store", toggleButton);
        add(appBar);

        TabPageManager tabPageManager = new TabPageManager(
            new TabPage("Pets", petManager),
            new TabPage("Species", speciesManager),
            new TabPage("Customers", customerManager),
            new TabPage("Purchases", purchaseManager)
        );

        tabPageManager.setClassName("tab-page-manager");

        add(tabPageManager);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        setDarkTheme(isDark());
    }

    boolean isDark() {
        Optional<Preferences> preferences = preferencesRepository.findById(getUserId());
        if(! preferences.isPresent()) return false;
        return preferences.get().getDarkMode();
    }

    void setDark(Boolean dark) {
        Optional<Preferences> preferences = preferencesRepository.findById(getUserId());
        if(! preferences.isPresent()) return;
        Preferences p = preferences.get();
        p.setDarkMode(dark);
        System.out.println("Set dark: "+dark.toString());
        preferencesRepository.saveOrUpdate(p);
    }

    void setDarkTheme(Boolean dark) {
        ThemeList themeList = UI.getCurrent().getElement().getThemeList();
        if (dark) {
            themeList.remove(Lumo.LIGHT);
            themeList.add(Lumo.DARK);
            setDark(true);
        } else {
            themeList.add(Lumo.LIGHT);
            themeList.remove(Lumo.DARK);
            setDark(false);
        }
    }

    void toggleDarkTheme() {
        setDarkTheme(!isDark());
    }

    long getUserId() {
        return -1;
    }
}
