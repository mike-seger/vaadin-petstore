package com.net128.application.vaadin.petstore.ui;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@CssImport("./styles/components/working-area.css")
public class WorkingArea extends VerticalLayout {

    private String CLASS_NAME = "working-area";

    public WorkingArea() {
        setClassName(CLASS_NAME);
        setSizeFull();
    }
}
