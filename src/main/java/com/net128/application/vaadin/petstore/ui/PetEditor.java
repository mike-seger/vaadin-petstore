package com.net128.application.vaadin.petstore.ui;

import com.net128.application.vaadin.petstore.model.Pet;
import com.net128.application.vaadin.petstore.model.Species;
import com.net128.application.vaadin.petstore.repo.PetRepository;
import com.net128.application.vaadin.petstore.repo.SpeciesRepository;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class PetEditor extends EntityEditor<Pet> {
    protected Select<Species> species;
    protected TextField name;

    private final SpeciesRepository speciesRepository;

    public PetEditor(PetRepository repository,
             SpeciesRepository speciesRepository,
             SpeciesEditor speciesEditor) {
        super(repository);
        this.speciesRepository = speciesRepository;
        layout();
        speciesEditor.addChangeHandler(() -> {
            species.removeAll();
            species.clear();
            species.setDataProvider(DataProvider.ofCollection(speciesRepository.findAll()));
        });
    }

    @Override
    protected void layout() {
        species = new Select<>();
        species.setDataProvider(DataProvider.ofCollection(speciesRepository.findAll()));
        species.setItemLabelGenerator(species -> species==null?"Select species...":species.getName());
        name = new TextField("Name");
        add(name, species);
        super.layout();
    }
}
