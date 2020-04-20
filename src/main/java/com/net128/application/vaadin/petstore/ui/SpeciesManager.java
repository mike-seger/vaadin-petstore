package com.net128.application.vaadin.petstore.ui;

import com.net128.application.vaadin.petstore.model.Species;
import com.net128.application.vaadin.petstore.repo.SpeciesRepository;
import com.vaadin.flow.component.button.Button;
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

    private final SpeciesRepository speciesRepository;
    private TextField nameFilter;

    public SpeciesManager(SpeciesRepository userRepository, SpeciesEditor userEditor) {
        super(userEditor);
        this.speciesRepository = userRepository;
        layout();
    }

    public void setupGrid(Grid<Species> grid) {
        grid.setColumns("name");
    }

    public HorizontalLayout createActionBar(EntityEditor<Species> userEditor) {
        nameFilter = new TextField();
        nameFilter.setPlaceholder("Find a name...");
        nameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        nameFilter.addValueChangeListener(e -> setGridData(listEntities()));
        final Button newUserButton = new Button("New Species...", VaadinIcon.PLUS.create());
        newUserButton.addClickListener(e -> userEditor.editNew());
        return new HorizontalLayout(nameFilter, newUserButton);
    }

    public List<Species> listEntities() {
        List<Species> users;
        String filterText = nameFilter.getValue();
        if (StringUtils.isEmpty(filterText)) {
            users = speciesRepository.findAll();
        } else {
            users = speciesRepository.findByNameContainingIgnoreCaseOrderById(filterText);
        }
        return users;
    }
}
