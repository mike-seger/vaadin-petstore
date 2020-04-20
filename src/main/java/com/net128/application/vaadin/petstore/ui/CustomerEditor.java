package com.net128.application.vaadin.petstore.ui;

import com.net128.application.vaadin.petstore.model.Customer;
import com.net128.application.vaadin.petstore.repo.CustomerRepository;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class CustomerEditor extends EntityEditor<Customer> {

    protected TextField firstName = new TextField("First name");
    protected TextField lastName = new TextField("Last name");

    @Autowired
    public CustomerEditor(CustomerRepository repository) {
        super(repository);
        layout();
    }

    protected void layout() {
        add(firstName, lastName);
        super.layout();
    }
}
