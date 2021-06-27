package com.net128.application.vaadin.petstore.ui;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@CssImport("./styles/components/custom-styles.css")
public class WorkingArea extends VerticalLayout {

    final private String CLASS_NAME = "working-area";

    public WorkingArea() {
        setClassName(CLASS_NAME);
        setMargin(false);
        setSpacing(false);
        setPadding(false);
    }
}
