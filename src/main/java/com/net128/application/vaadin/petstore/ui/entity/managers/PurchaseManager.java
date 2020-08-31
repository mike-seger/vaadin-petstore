package com.net128.application.vaadin.petstore.ui.entity.managers;

import com.net128.application.vaadin.petstore.model.Purchase;
import com.net128.application.vaadin.petstore.repo.PurchaseRepository;
import com.net128.application.vaadin.petstore.ui.entity.generic.EntityEditor;
import com.net128.application.vaadin.petstore.ui.entity.generic.EntityManager;
import com.net128.application.vaadin.petstore.ui.entity.editors.PurchaseEditor;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.util.Comparator;
import java.util.List;

@SpringComponent
@UIScope
public class PurchaseManager extends EntityManager<Purchase> {

    final private PurchaseRepository repository;
    private TextField nameFilter;
    private DatePicker startDate;
    private DatePicker endDate;

    public PurchaseManager(PurchaseRepository repository, PurchaseEditor editor) {
        super(editor);
        this.repository = repository;
    }

    public void setupGrid(Grid<Purchase> grid) {
        grid.removeAllColumns();
        grid.addColumn(new LocalDateTimeRenderer<>(
            Purchase::getDate, "yyyy-MM-dd HH:mm"))
            .setComparator(Comparator.comparing(Purchase::getDate)).setSortable(true).setHeader("Purchased");
        grid.addColumn("customer.lastName").setHeader("Customer Name");
        grid.addColumn("customer.firstName").setHeader("Customer First Name");
        grid.addColumn("pet.name").setHeader("Pet Name");
    }

    public HorizontalLayout createActionBar(EntityEditor<Purchase> editor) {
        nameFilter = new TextField();
        nameFilter.setPlaceholder("Find in any name...");
        nameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        nameFilter.addValueChangeListener(e -> updateGrid());
        nameFilter.setPrefixComponent(VaadinIcon.SEARCH.create());
        startDate = new DatePicker();
        startDate.setPlaceholder("Starting from...");
        startDate.addValueChangeListener(e -> updateGrid());
        endDate = new DatePicker();
        endDate.setPlaceholder("...ending");
        endDate.addValueChangeListener(e -> updateGrid());
        return new HorizontalLayout(nameFilter, startDate, endDate);
    }

    public List<Purchase> filter() {
        return repository.filter(nameFilter.getValue(), startDate.getValue(), endDate.getValue());
    }
}
