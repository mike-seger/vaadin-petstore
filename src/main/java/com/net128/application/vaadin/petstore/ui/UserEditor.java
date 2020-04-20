package com.net128.application.vaadin.petstore.ui;

import com.net128.application.vaadin.petstore.model.User;
import com.net128.application.vaadin.petstore.repo.UserRepository;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class UserEditor extends EntityEditor<User> {

    protected TextField firstName = new TextField("First name");
    protected TextField lastName = new TextField("Last name");

    @Autowired
    public UserEditor(UserRepository repository) {
        super(repository);
        layout();
    }

    protected void layout() {
        add(firstName, lastName);
        super.layout();
    }
}
