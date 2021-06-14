package com.net128.application.vaadin.petstore.ui.entity.managers;

import com.net128.application.vaadin.petstore.model.Identifiable;
import com.net128.application.vaadin.petstore.model.Pet;
import com.net128.application.vaadin.petstore.model.Species;
import com.net128.application.vaadin.petstore.repo.PetRepository;
import com.net128.application.vaadin.petstore.repo.SpeciesRepository;
import com.net128.application.vaadin.petstore.ui.entity.editors.PetEditor;
import com.net128.application.vaadin.petstore.ui.entity.generic.EntityEditor;
import com.net128.application.vaadin.petstore.ui.entity.generic.EntityManager;
import com.vaadin.flow.component.grid.Grid;
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
    final private SpeciesRepository speciesRepository;
    final private Select<Species> speciesFilter = new Select<>();

    public PetManager(PetRepository petRepository,
              PetEditor petEditor,
              SpeciesRepository speciesRepository
        ) {
        super(petEditor);
        this.petRepository = petRepository;
        this.speciesRepository = speciesRepository;
        speciesFilter.setDataProvider(DataProvider.ofCollection(speciesRepository.findAll()));
    }

    public void entityChanged(Identifiable entity) {
        speciesFilter.removeAll();
        speciesFilter.setDataProvider(DataProvider.ofCollection(speciesRepository.findAll()));
        super.entityChanged(entity);
    }

    public void setupGrid(Grid<Pet> grid) {
        grid.setColumns("name", "species.name");
        grid.getColumnByKey("species.name").setHeader("Species");
    }

    public HorizontalLayout createActionBar(EntityEditor<Pet> petEditor) {
        speciesFilter.setEmptySelectionAllowed(true);
        speciesFilter.setItemLabelGenerator(species -> species==null?"Select species...":species.getName());
        speciesFilter.addValueChangeListener(e -> updateGrid());
        return new HorizontalLayout(speciesFilter);
    }

    public List<Pet> filter() {
        return petRepository.filter(speciesFilter.getValue());
    }
}
