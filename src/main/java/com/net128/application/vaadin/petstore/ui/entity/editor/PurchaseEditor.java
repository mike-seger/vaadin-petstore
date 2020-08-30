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
import lombok.extern.slf4j.Slf4j;

@SpringComponent
@UIScope
@Slf4j
public class PurchaseEditor extends EntityEditor<Purchase> {

    protected Select<Customer> customer;
    protected Select<Pet> pet;

    final private CustomerRepository customerRepository;
    final private PetRepository petRepository;

    public PurchaseEditor(PurchaseRepository repository,
            CustomerRepository customerRepository,
            PetRepository petRepository) {
        super(repository);
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }

    public void layout() {
        customer = new Select<>();
        customer.setItemLabelGenerator(customer -> customer==null?"Select customer...":(customer.getLastName()+" "+customer.getFirstName()));
        customer.setLabel("Customer");
        pet = new Select<>();
        pet.setLabel("Pet");
        pet.setItemLabelGenerator(pet -> pet==null?"Select pet...":pet.getName());

        setEntityChangedHandler(entity -> {
            if(entity==null || entity instanceof Customer) {
                customer.removeAll();
                customer.setDataProvider(DataProvider.ofCollection(customerRepository.findAllOrdered()));
            }
            if(entity==null || entity instanceof Pet) {
                pet.removeAll();
                pet.setDataProvider(DataProvider.ofCollection(petRepository.findAllOrdered()));
            }
        });

        add(customer, pet);
        super.layout();
    }
}
