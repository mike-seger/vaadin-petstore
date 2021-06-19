package com.net128.application.vaadin.petstore.ui.entity.editors;

import com.net128.application.vaadin.petstore.model.Pet;
import com.net128.application.vaadin.petstore.model.Species;
import com.net128.application.vaadin.petstore.repo.PetRepository;
import com.net128.application.vaadin.petstore.repo.SpeciesRepository;
import com.net128.application.vaadin.petstore.ui.entity.generic.EntityEditor;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.util.List;

@SpringComponent
@UIScope
public class PetEditor extends EntityEditor<Pet> {
    protected ComboBox<Species> species = new ComboBox<>();
    protected TextField name = new TextField();
    protected NumberField price = new NumberField();

    final private SpeciesRepository speciesRepository;

    public PetEditor(PetRepository repository,
             SpeciesRepository speciesRepository) {
        super(repository);
        this.speciesRepository = speciesRepository;
    }

    @Override
    public List<Component> createInputFields() {
        species.setItemLabelGenerator(species -> species==null?"":species.getName());
        setEntityChangedHandler(entity ->  {
            species.setItems(DataProvider.ofCollection(speciesRepository.findAll()));
            species.setRequiredIndicatorVisible(true);
        });
        return componentList(name, species, price);
    }
}
