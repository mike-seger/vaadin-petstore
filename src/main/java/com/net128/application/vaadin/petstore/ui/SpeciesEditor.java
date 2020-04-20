package com.net128.application.vaadin.petstore.ui;

import com.net128.application.vaadin.petstore.model.Species;
import com.net128.application.vaadin.petstore.repo.SpeciesRepository;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class SpeciesEditor extends EntityEditor<Species> {

    protected TextField name = new TextField("Name");

    @Autowired
    public SpeciesEditor(SpeciesRepository repository) {
        super(repository);
        layout();
    }

    protected void layout() {
        add(name);
        super.layout();
    }
}
