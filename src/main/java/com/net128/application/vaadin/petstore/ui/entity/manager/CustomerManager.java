package com.net128.application.vaadin.petstore.ui.entity.manager;

import com.net128.application.vaadin.petstore.model.Customer;
import com.net128.application.vaadin.petstore.repo.CustomerRepository;
import com.net128.application.vaadin.petstore.ui.entity.EntityEditor;
import com.net128.application.vaadin.petstore.ui.entity.EntityManager;
import com.net128.application.vaadin.petstore.ui.entity.editor.CustomerEditor;
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
public class CustomerManager extends EntityManager<Customer> {

    final private CustomerRepository customerRepository;
    final private TextField customerNameFilter = new TextField();

    public CustomerManager(CustomerRepository customerRepository, CustomerEditor customerEditor) {
        super(customerEditor);
        this.customerRepository = customerRepository;
    }

    public void setupGrid(Grid<Customer> grid) {
        grid.setColumns("firstName", "lastName");
    }

    public HorizontalLayout createActionBar(EntityEditor<Customer> customerEditor) {
        customerNameFilter.setPlaceholder("Find in any name...");
        customerNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        customerNameFilter.addValueChangeListener(e -> updateGrid());
        customerNameFilter.setPrefixComponent(VaadinIcon.SEARCH.create());
        return new HorizontalLayout(customerNameFilter);
    }

    public List<Customer> list() {
        return customerRepository.filter(customerNameFilter.getValue());
    }
}
