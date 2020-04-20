package com.net128.application.vaadin.petstore.ui;

import com.net128.application.vaadin.petstore.model.Customer;
import com.net128.application.vaadin.petstore.repo.CustomerRepository;
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
public class CustomerManager extends EntityManager<Customer> {

    private final CustomerRepository customerRepository;
    private TextField customerNameFilter;

    public CustomerManager(CustomerRepository customerRepository, CustomerEditor customerEditor) {
        super(customerEditor);
        this.customerRepository = customerRepository;
        layout();
    }

    public void setupGrid(Grid<Customer> grid) {
        grid.setColumns("firstName", "lastName");
    }

    public HorizontalLayout createActionBar(EntityEditor<Customer> customerEditor) {
        customerNameFilter = new TextField();
        customerNameFilter.setPlaceholder("Find in any name...");
        customerNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        customerNameFilter.addValueChangeListener(e -> setGridData(list()));
        final Button newCustomerButton = new Button("New Customer...", VaadinIcon.PLUS.create());
        newCustomerButton.addClickListener(e -> customerEditor.editNew());
        return new HorizontalLayout(customerNameFilter, newCustomerButton);
    }

    public List<Customer> list() {
        List<Customer> customers;
        String filterText = customerNameFilter.getValue();
        if (StringUtils.isEmpty(filterText)) {
            customers = customerRepository.findAll();
        } else {
            customers = customerRepository.findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrderById(filterText, filterText);
        }
        return customers;
    }
}
