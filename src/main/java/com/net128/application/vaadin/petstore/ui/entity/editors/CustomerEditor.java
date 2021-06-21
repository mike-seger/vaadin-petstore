package com.net128.application.vaadin.petstore.ui.entity.editors;

import com.net128.application.vaadin.petstore.model.Country;
import com.net128.application.vaadin.petstore.model.Customer;
import com.net128.application.vaadin.petstore.repo.CountryRepository;
import com.net128.application.vaadin.petstore.repo.CustomerRepository;
import com.net128.application.vaadin.petstore.ui.entity.generic.EntityEditor;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.util.List;

@SpringComponent
@UIScope
public class CustomerEditor extends EntityEditor<Customer> {

    protected TextField firstName = new TextField();
    protected TextField lastName = new TextField();
    protected TextField address = new TextField();
    protected TextField zipCode = new TextField();
    protected TextField city = new TextField();
    protected ComboBox<Country> country = new ComboBox<>();
    protected TextField phone = new TextField();

    final private CountryRepository countryRepository;

    public CustomerEditor(CustomerRepository repository, CountryRepository countryRepository) {
        super(repository);
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Component> createInputFields() {
        country.setItemLabelGenerator(country -> country==null?"":country.getEmoji()+" "+country.getName());
        setEntityChangedHandler(entity ->  {
            country.setItems(DataProvider.ofCollection(countryRepository.findAllOrdered()));
            country.setRequiredIndicatorVisible(true);
        });
        return componentList(firstName, lastName, address, zipCode, city, country, phone);
    }
}
