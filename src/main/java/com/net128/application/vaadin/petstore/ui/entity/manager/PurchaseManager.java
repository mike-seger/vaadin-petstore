package com.net128.application.vaadin.petstore.ui.entity.manager;

import com.net128.application.vaadin.petstore.model.Purchase;
import com.net128.application.vaadin.petstore.repo.PurchaseRepository;
import com.net128.application.vaadin.petstore.ui.entity.EntityEditor;
import com.net128.application.vaadin.petstore.ui.entity.EntityManager;
import com.net128.application.vaadin.petstore.ui.entity.editor.PurchaseEditor;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.util.List;

@SpringComponent
@UIScope
public class PurchaseManager extends EntityManager<Purchase> {

    final private PurchaseRepository repository;
    final private TextField nameFilter = new TextField();

    public PurchaseManager(PurchaseRepository repository, PurchaseEditor editor) {
        super(editor);
        this.repository = repository;
    }

    public void setupGrid(Grid<Purchase> grid) {
        grid.setColumns("customer.lastName", "customer.firstName", "pet.name");
    }

    public HorizontalLayout createActionBar(EntityEditor<Purchase> editor) {
        nameFilter.setPlaceholder("Find in any name...");
        nameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        nameFilter.addValueChangeListener(e -> updateGrid());
        nameFilter.setPrefixComponent(VaadinIcon.SEARCH.create());
        return new HorizontalLayout(nameFilter);
    }

    public List<Purchase> list() {
        return repository.filter(/*nameFilter.getValue()*/);
    }
}
