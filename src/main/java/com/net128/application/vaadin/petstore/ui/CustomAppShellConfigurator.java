package com.net128.application.vaadin.petstore.ui;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Push
@Theme(themeClass = Lumo.class, variant = Lumo.DARK)
public class CustomAppShellConfigurator implements AppShellConfigurator {
}
