package com.net128.application.vaadin.petstore.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TabPageManager extends VerticalLayout {
    public TabPageManager(TabPage ... tabPages) {
        setSizeFull();
        setMargin(false);
        setSpacing(false);
        setPadding(false);

        List<TabPage> tabPageList = new ArrayList<>(Arrays.asList(tabPages));
        Map<Tab, Component> tabsToPages = new HashMap<>();
        tabPageList.forEach(tp -> {
            tabsToPages.put(tp.getTab(), tp.getPage());
            tp.getPage().setVisible(false);
        });
        Tab [] tabArray = tabPageList.stream().map(tp -> tp.getTab()).collect(Collectors.toList()).toArray(new Tab [tabPageList.size()]);
        Component [] componentArray = tabPageList.stream().map(tp -> tp.getPage()).collect(Collectors.toList()).toArray(new Component [tabPageList.size()]);
        componentArray[0].setVisible(true);
        Tabs tabs = new Tabs(tabArray);
        Div pages = new Div(componentArray);
        pages.setSizeFull();
        Set<Component> pagesShown = Stream.of(componentArray[0]).collect(Collectors.toSet());

        tabs.addSelectedChangeListener(event -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });

        tabs.setSizeFull();
        VerticalLayout managedTabs = new VerticalLayout();
        managedTabs.add(tabs);
        managedTabs.add(pages);

        add(managedTabs);
    }
}
