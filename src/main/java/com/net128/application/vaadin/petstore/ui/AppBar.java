package com.net128.application.vaadin.petstore.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.Optional;
/*
https://vaadin.com/components/vaadin-ordered-layout/java-examples
 */
@CssImport("./styles/components/app-bar.css")
public class AppBar extends Header {

	final private String CLASS_NAME = "app-bar";

	private H1 title;
	private HorizontalLayout h = new HorizontalLayout();

	public AppBar(String title, Component component) {
		setClassName(CLASS_NAME);
		setWidthFull();
		setHeight("40px");
		Text titleText = new Text("Pet Store");
		HorizontalLayout h = new HorizontalLayout();
		//h.setMargin(false);
		h.setSpacing(false);
		h.setPadding(false);
		//h.setWidthFull();
		h.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
		h.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
		//h.setVerticalComponentAlignment(FlexComponent.Alignment.START, titleText);
		//h.setVerticalComponentAlignment(FlexComponent.Alignment.STRETCH, component);
		h.add(titleText, new HorizontalLayout(component, new Div()));
		add(h);
		initTitle(title);
	}

	private void initTitle(String title) {
		this.title = new H1(title);
		this.title.setClassName(CLASS_NAME + "__title");
	}

	public Optional<String> getTitle() {
		return Optional.ofNullable(this.title.getText());
	}

	public void setTitle(String title) {
		this.title.setText(title);
	}

	public void reset() {
		title.setText("");
	}
}
