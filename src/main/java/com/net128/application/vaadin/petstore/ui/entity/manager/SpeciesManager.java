package com.net128.application.vaadin.petstore.ui.entity.manager;

import com.net128.application.vaadin.petstore.model.Species;
import com.net128.application.vaadin.petstore.repo.SpeciesRepository;
import com.net128.application.vaadin.petstore.ui.entity.EntityEditor;
import com.net128.application.vaadin.petstore.ui.entity.EntityManager;
import com.net128.application.vaadin.petstore.ui.entity.editor.SpeciesEditor;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.util.StringUtils;

import java.util.List;

@SpringComponent
@UIScope
public class SpeciesManager extends EntityManager<Species> {

    final private SpeciesRepository speciesRepository;
    final private TextField nameFilter = new TextField();

    public SpeciesManager(SpeciesRepository speciesRepository, SpeciesEditor speciesEditor) {
        super(speciesEditor);
        this.speciesRepository = speciesRepository;
    }

    public void setupGrid(Grid<Species> grid) {
        grid.removeAllColumns();
        grid.addColumn("name").setAutoWidth(true);
        grid.setSizeUndefined();
        grid.setHeightFull();
    }

    public HorizontalLayout createActionBar(EntityEditor<Species> speciesEditor) {
        nameFilter.setPlaceholder("Find by name...");
        nameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        nameFilter.addValueChangeListener(e -> updateGrid());
        nameFilter.setPrefixComponent(VaadinIcon.SEARCH.create());
        return new HorizontalLayout(nameFilter);
    }

    public List<Species> list() {
        List<Species> species;
        String filterText = nameFilter.getValue();
        if (StringUtils.isEmpty(filterText)) {
            species = speciesRepository.findAllOrdered();
        } else {
            species = speciesRepository.findByNameContainingIgnoreCaseOrderById(filterText);
        }
        return species;
    }
}
