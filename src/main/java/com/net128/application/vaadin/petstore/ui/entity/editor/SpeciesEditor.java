package com.net128.application.vaadin.petstore.ui.entity.editor;

import com.net128.application.vaadin.petstore.model.Species;
import com.net128.application.vaadin.petstore.repo.SpeciesRepository;
import com.net128.application.vaadin.petstore.ui.entity.generic.EntityEditor;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.util.List;

@SpringComponent
@UIScope
public class SpeciesEditor extends EntityEditor<Species> {

    protected TextField name = new TextField();

    public SpeciesEditor(SpeciesRepository repository) {
        super(repository);
    }

    @Override
    public List<Component> createInputFields() {
        return componentList(name);
    }
}
