package com.net128.application.vaadin.petstore.ui.entity.editor;

import com.net128.application.vaadin.petstore.model.Customer;
import com.net128.application.vaadin.petstore.model.Pet;
import com.net128.application.vaadin.petstore.model.Purchase;
import com.net128.application.vaadin.petstore.repo.CustomerRepository;
import com.net128.application.vaadin.petstore.repo.PetRepository;
import com.net128.application.vaadin.petstore.repo.PurchaseRepository;
import com.net128.application.vaadin.petstore.ui.entity.EntityEditor;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class PurchaseEditor extends EntityEditor<Purchase> {

    protected Select<Customer> customer;
    protected Select<Pet> pet;

    final private PetRepository petRepository;
    final private CustomerRepository customerRepository;

    public PurchaseEditor(PurchaseRepository repository, PetRepository petRepository, CustomerRepository customerRepository) {
        super(repository);
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public void layout() {
        customer = new Select<>();
        customer.setItemLabelGenerator(customer -> customer==null?"Select customer...":(customer.getLastName()+" "+customer.getFirstName()));
        setEntityChangedHandler(entity ->  {
            customer.removeAll();
            customer.setDataProvider(DataProvider.ofCollection(customerRepository.findAll()));
        });

        pet = new Select<>();
        pet.setItemLabelGenerator(pet -> pet==null?"Select pet...":pet.getName());
        setEntityChangedHandler(entity ->  {
            pet.removeAll();
            pet.setDataProvider(DataProvider.ofCollection(petRepository.findAll()));
        });

        add(customer, pet);
        super.layout();
    }
}
