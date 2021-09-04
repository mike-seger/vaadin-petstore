package com.net128.application.vaadin.petstore.repo;

import com.net128.application.vaadin.petstore.model.Country;
import com.net128.application.vaadin.petstore.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.util.StringUtils;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    String orderClause =  "order by c.firstName, c.lastName, c.country.name";
    @Query("""
            select c from Customer c
            where lower(concat(c.firstName, ' ',c.lastName,' ',c.address)) like lower(concat('%',:text, '%')) 
            and (:countryId is null or c.country.id = :countryId) 
            """+ orderClause)
    List <Customer> filterFull(@Param("text") String text, @Param("countryId") Long countryId);

    @Query("select c from Customer c where :countryId is null or c.country.id = :countryId " + orderClause)
    List <Customer> filterCountry(@Param("countryId") Long countryId);

    default List<Customer> filter(String text, Country country) {
        var countryId=country==null?null:country.getId();
        if(!StringUtils.hasText(text)) {
            return filterCountry(countryId);
        }
        return filterFull(text, countryId);
    }

    List<Customer> findByOrderByLastNameAscFirstNameAscIdAsc();
    default List<Customer> findAllOrderedLastFirst() {
        return findByOrderByLastNameAscFirstNameAscIdAsc();
    }
}
