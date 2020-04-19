package com.net128.application.vaadin.petstore.ui;

import com.net128.application.vaadin.petstore.model.Pet;
import com.net128.application.vaadin.petstore.model.Species;
import com.net128.application.vaadin.petstore.repo.PetRepository;
import com.net128.application.vaadin.petstore.repo.SpeciesRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.util.List;

@SpringComponent
@UIScope
public class PetManager extends EntityManager<Pet> {
    final private PetRepository petRepository;
    private Select<Species> speciesFilter;

    public PetManager(PetRepository petRepository, PetEditor petEditor, SpeciesRepository speciesRepository) {
        super(petEditor);
        this.petRepository = petRepository;
        layout();
        speciesFilter.setDataProvider(DataProvider.ofCollection(speciesRepository.findAll()));
    }

    public void setupGrid(Grid<Pet> grid) {
        grid.setColumns("name", "species.name");
        grid.getColumnByKey("species.name").setHeader("Species");
    }

    public HorizontalLayout createActionBar(EntityEditor<Pet> entityEditor) {
        speciesFilter = new Select<>();
        speciesFilter.setEmptySelectionAllowed(true);
        speciesFilter.setItemLabelGenerator(species -> species==null?"":species.getName());
        speciesFilter.addValueChangeListener(e -> setGridData(listEntities()));
        final Button addPetButton = new Button("Add Pet", VaadinIcon.PLUS.create());
        addPetButton.addClickListener(e -> entityEditor.editNew());
        return new HorizontalLayout(speciesFilter, addPetButton);
    }

    public List<Pet> listEntities() {
        List<Pet> pets;
        if(speciesFilter.getOptionalValue().isPresent()) {
            Species species = speciesFilter.getOptionalValue().get();
            if(species==null) {
                pets = petRepository.findAll();
            } else {
                pets = petRepository.findBySpecies(species);
            }
        } else {
            pets = petRepository.findAll();
        }
        return pets;
    }
}
