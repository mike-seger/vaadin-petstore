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

    public CustomerEditor(CustomerRepository repository) {
        super(repository);
    }

    public void layout() {
        firstName = new TextField("First name");
        lastName = new TextField("Last name");
        add(firstName, lastName);
        super.layout();
    }
}
