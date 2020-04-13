package com.net128.application.vaadin.petstore.ui;


import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;

import java.util.Optional;

@CssImport("./styles/components/app-bar.css")
public class AppBar extends Header {

	private String CLASS_NAME = "app-bar";

	private H1 title;

	public AppBar(String title) {
		setClassName(CLASS_NAME);
		setWidthFull();
		setHeight("40px");

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
