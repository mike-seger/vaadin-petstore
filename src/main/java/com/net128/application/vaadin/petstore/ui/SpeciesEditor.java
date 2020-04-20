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
    private final PetManager petManager;
    private final PetEditor petEditor;

    @Autowired
    public SpeciesEditor(SpeciesRepository repository,
            PetManager petManager,
            PetEditor petEditor) {
        super(repository);
        this.petManager = petManager;
        this.petEditor = petEditor;
        layout();
    }

    protected void layout() {
        add(name);
        super.layout();
    }

    void delete() {
        super.delete();
        petManager.entityChanged();
        petEditor.entityChanged();
    }

    void save() {
        super.save();
        petManager.entityChanged();
        petEditor.entityChanged();
    }
}
