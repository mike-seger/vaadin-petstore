package com.net128.application.vaadin.petstore.ui.entity.editor;

import com.net128.application.vaadin.petstore.model.Customer;
import com.net128.application.vaadin.petstore.repo.CustomerRepository;
import com.net128.application.vaadin.petstore.ui.entity.EntityEditor;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class CustomerEditor extends EntityEditor<Customer> {

    protected TextField firstName;
    protected TextField lastName;
    protected TextField address;
    protected TextField phone;

    public CustomerEditor(CustomerRepository repository) {
        super(repository);
    }

    public void layout() {
        firstName = new TextField();
        firstName.setPlaceholder("Enter first mame...");
        lastName = new TextField();
        lastName.setPlaceholder("Enter last mame...");
        address = new TextField();
        address.setPlaceholder("Enter address...");
        phone = new TextField();
        phone.setPlaceholder("Enter phone number...");
        add(firstName, lastName, address, phone);
        super.layout();
    }
}
