package com.net128.application.vaadin.petstore.ui.entity.editors;

import com.net128.application.vaadin.petstore.model.Customer;
import com.net128.application.vaadin.petstore.repo.CustomerRepository;
import com.net128.application.vaadin.petstore.ui.entity.generic.EntityEditor;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.util.List;

@SpringComponent
@UIScope
public class CustomerEditor extends EntityEditor<Customer> {

    protected TextField firstName = new TextField();
    protected TextField lastName = new TextField();
    protected TextField address = new TextField();
    protected TextField phone = new TextField();

    public CustomerEditor(CustomerRepository repository) {
        super(repository);
    }

    public List<Component> createInputFields() {
        return componentList(firstName, lastName, address, phone);
    }
}
