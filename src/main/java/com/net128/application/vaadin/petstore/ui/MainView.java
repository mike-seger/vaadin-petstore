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
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import lombok.extern.slf4j.Slf4j;

@Route
@CssImport(value = "./styles/components/custom-styles.css")
@CssImport(value = "./styles/components/custom-styles.css", themeFor = "vaadin-grid")
@CssImport(value = "./styles/components/custom-styles.css", themeFor = "vaadin-select-overlay")
@JsModule("@vaadin/vaadin-lumo-styles/presets/compact.js")
@Slf4j
public class MainView extends FlexLayout implements KeyNotifier {
    private final PreferencesRepository preferencesRepository;
    private final TabPageManager tabPageManager;

    public MainView(CustomerManager customerManager,
            PetManager petManager,
            SpeciesManager speciesManager,
            PurchaseManager purchaseManager,
            PreferencesRepository preferencesRepository) {

        this.preferencesRepository = preferencesRepository;
        setClassName("main-view");
        var toggleButton = new Button(VaadinIcon.MENU.create(), click -> toggleDarkTheme());

        toggleButton.addClickShortcut(Key.KEY_T, KeyModifier.ALT);

        var appBar = new AppBar("Pet Store", toggleButton);
        add(appBar);

        tabPageManager = new TabPageManager(
            new TabPage("Pets", petManager),
            new TabPage("Species", speciesManager),
            new TabPage("Customers", customerManager),
            new TabPage("Purchases", purchaseManager)
        );

        tabPageManager.addPageChangeConsumer(label ->
            preferencesRepository.findById(getUserId()).ifPresent(preferences -> {
                preferences.setCurrentTab(label);
                preferencesRepository.saveOrUpdate(preferences);

            }));

        tabPageManager.setClassName("tab-page-manager");
        add(tabPageManager);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        preferencesRepository.findById(getUserId()).ifPresentOrElse(
            preferences -> {
                setDarkTheme(preferences.getDarkMode());
                String currentTab = preferences.getCurrentTab();
                if(currentTab!=null) tabPageManager.switchPage(currentTab);
        }, () -> {});
    }

    boolean isDark() {
        return preferencesRepository.findById(getUserId())
            .map(Preferences::getDarkMode).orElse(false);
    }

    void setDark(Boolean dark) {
        preferencesRepository.findById(getUserId()).ifPresent(
            (preferences) -> {
                preferences.setDarkMode(dark);
                log.info("Set dark: {}", dark);
                preferencesRepository.saveOrUpdate(preferences);
            });
    }

    void setDarkTheme(Boolean dark) {
        var themeList = UI.getCurrent().getElement().getThemeList();
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
