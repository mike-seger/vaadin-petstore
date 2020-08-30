package com.net128.application.vaadin.petstore.ui.entity.editor;

import com.net128.application.vaadin.petstore.model.Species;
import com.net128.application.vaadin.petstore.repo.SpeciesRepository;
import com.net128.application.vaadin.petstore.ui.entity.EntityEditor;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class SpeciesEditor extends EntityEditor<Species> {

    protected TextField name;

    public SpeciesEditor(SpeciesRepository repository) {
        super(repository);
    }

    public void layout() {
        name = new TextField("Name");
        add(name);
        super.layout();
    }
}
