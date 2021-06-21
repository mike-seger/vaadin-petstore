package com.net128.application.vaadin.petstore.ui.entity.managers;

import com.net128.application.vaadin.petstore.model.Country;
import com.net128.application.vaadin.petstore.model.Customer;
import com.net128.application.vaadin.petstore.model.Species;
import com.net128.application.vaadin.petstore.repo.CountryRepository;
import com.net128.application.vaadin.petstore.repo.CustomerRepository;
import com.net128.application.vaadin.petstore.ui.entity.editors.CustomerEditor;
import com.net128.application.vaadin.petstore.ui.entity.generic.EntityEditor;
import com.net128.application.vaadin.petstore.ui.entity.generic.EntityManager;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.util.List;

@SpringComponent
@UIScope
public class CustomerManager extends EntityManager<Customer> {

    final private CustomerRepository customerRepository;
    final private TextField customerFilter = new TextField();
    final private Select<Country> countryFilter = new Select<>();
    final private CountryRepository countryRepository;

    public CustomerManager(CustomerRepository customerRepository, CustomerEditor customerEditor, CountryRepository countryRepository) {
        super(customerEditor);
        this.customerRepository = customerRepository;
        this.countryRepository = countryRepository;
        countryFilter.setItems(DataProvider.ofCollection(countryRepository.findAllOrdered()));
    }

    public void setupGrid(Grid<Customer> grid) {
        grid.removeAllColumns();
        grid.addColumn(TemplateRenderer.<Customer>of("[[item.customer]]").withProperty("customer",
            customer -> customer.getFirstName() + " " + customer.getLastName())).setHeader("Customer");
        grid.addColumn(TemplateRenderer.<Customer>of("[[item.address]]").withProperty("address",
            address -> address.getAddress() + ", " + address.getCity())).setHeader("Address");
        grid.addColumns("phone");
    }

    public HorizontalLayout createActionBar(EntityEditor<Customer> customerEditor) {
        customerFilter.setPlaceholder("Find customer...");
        customerFilter.setValueChangeMode(ValueChangeMode.EAGER);
        customerFilter.addValueChangeListener(e -> updateGrid());
        customerFilter.setPrefixComponent(VaadinIcon.SEARCH.create());

        countryFilter.setEmptySelectionAllowed(true);
        countryFilter.setItemLabelGenerator(country -> country==null?"Select country...":country.getName());
        countryFilter.addValueChangeListener(e -> updateGrid());

        return new HorizontalLayout(customerFilter, countryFilter);
    }

    public List<Customer> filter() {

        return customerRepository.filter(customerFilter.getValue(), countryFilter.getValue());
    }
}
