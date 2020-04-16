package com.net128.application.vaadin.petstore.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.tabs.Tab;

public class TabPage {
    Tab tab;
    Component page;

    public TabPage(Tab tab, Component page) {
        this.tab = tab;
        this.page = page;
    }

    public TabPage(String tabLabel, Component page) {
        this(new Tab(tabLabel), page);
    }
}
