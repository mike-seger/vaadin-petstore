package com.net128.application.vaadin.petstore.ui.entity.editor;

import com.net128.application.vaadin.petstore.model.Pet;
import com.net128.application.vaadin.petstore.model.Species;
import com.net128.application.vaadin.petstore.repo.PetRepository;
import com.net128.application.vaadin.petstore.repo.SpeciesRepository;
import com.net128.application.vaadin.petstore.ui.entity.EntityEditor;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class PetEditor extends EntityEditor<Pet> {
    protected Select<Species> species;
    protected TextField name;
    protected NumberField price;

    final private SpeciesRepository speciesRepository;

    public PetEditor(PetRepository repository,
             SpeciesRepository speciesRepository) {
        super(repository);
        this.speciesRepository = speciesRepository;
    }

    @Override
    public void layout() {
        species = new Select<>();
        species.setLabel("Species");
        species.setItemLabelGenerator(species -> species==null?"Select species...":species.getName());
        setEntityChangedHandler(entity ->  {
            species.removeAll();
            species.setDataProvider(DataProvider.ofCollection(speciesRepository.findAll()));
            species.setRequiredIndicatorVisible(true);
        });
        name = new TextField("Name");
        price = new NumberField("Price (USD)");
        price.setMin(0.1);
        add(name, species, price);
        super.layout();
    }
}
