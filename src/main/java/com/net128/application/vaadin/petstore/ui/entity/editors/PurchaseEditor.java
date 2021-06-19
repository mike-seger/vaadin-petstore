package com.net128.application.vaadin.petstore.ui.entity.editors;

import com.net128.application.vaadin.petstore.model.Customer;
import com.net128.application.vaadin.petstore.model.Pet;
import com.net128.application.vaadin.petstore.model.Purchase;
import com.net128.application.vaadin.petstore.repo.CustomerRepository;
import com.net128.application.vaadin.petstore.repo.PetRepository;
import com.net128.application.vaadin.petstore.repo.PurchaseRepository;
import com.net128.application.vaadin.petstore.ui.entity.generic.EntityEditor;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@SpringComponent
@UIScope
@Slf4j
public class PurchaseEditor extends EntityEditor<Purchase> {

    protected ComboBox<Customer> customer;
    protected ComboBox<Pet> pet;

    final private CustomerRepository customerRepository;
    final private PetRepository petRepository;

    public PurchaseEditor(PurchaseRepository repository,
            CustomerRepository customerRepository,
            PetRepository petRepository) {
        super(repository);
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }

    @Override
    public List<Component> createInputFields() {
        customer = new ComboBox<>();
        customer.setItemLabelGenerator(c -> c==null?"":c.getLastName()+" "+c.getFirstName());
        pet = new ComboBox<>();
        pet.setItemLabelGenerator(c -> c==null?"":c.getName());
        setEntityChangedHandler(entity -> {
            if(entity==null || entity instanceof Customer) {
                customer.setItems(DataProvider.ofCollection(customerRepository.findAllOrderedLastFirst()));
            }
            if(entity==null || entity instanceof Pet) {
                pet.setItems(DataProvider.ofCollection(petRepository.findAllOrdered()));
            }
        });

        return componentList(customer, pet);
    }
}
