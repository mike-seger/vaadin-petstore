package com.net128.application.vaadin.petstore.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.tabs.Tab;
import lombok.Data;

@Data
public class TabPage {
    private Tab tab;
    private Component page;

    public TabPage(Tab tab, Component page) {
        this.tab = tab;
        this.page = page;
    }

    public String getLabel() {
        return tab.getLabel();
    }

    public TabPage(String tabLabel, Component page) {
        this(new Tab(tabLabel), page);
    }
}
