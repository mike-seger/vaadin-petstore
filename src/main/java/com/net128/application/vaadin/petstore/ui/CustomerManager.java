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
    private TextField userNameFilter;

    public CustomerManager(CustomerRepository customerRepository, CustomerEditor customerEditor) {
        super(customerEditor);
        this.customerRepository = customerRepository;
        layout();
    }

    public void setupGrid(Grid<Customer> grid) {
        grid.setColumns("firstName", "lastName");
    }

    public HorizontalLayout createActionBar(EntityEditor<Customer> userEditor) {
        userNameFilter = new TextField();
        userNameFilter.setPlaceholder("Find in any name...");
        userNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        userNameFilter.addValueChangeListener(e -> setGridData(listEntities()));
        final Button newUserButton = new Button("New User...", VaadinIcon.PLUS.create());
        newUserButton.addClickListener(e -> userEditor.editNew());
        return new HorizontalLayout(userNameFilter, newUserButton);
    }

    public List<Customer> listEntities() {
        List<Customer> customers;
        String filterText = userNameFilter.getValue();
        if (StringUtils.isEmpty(filterText)) {
            customers = customerRepository.findAll();
        } else {
            customers = customerRepository.findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrderById(filterText, filterText);
        }
        return customers;
    }
}
