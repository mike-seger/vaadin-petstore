package com.net128.application.vaadin.petstore.repo;

import com.net128.application.vaadin.petstore.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    String nameRestriction = "lower(p.customer.lastName) like lower(concat('%', :name,'%')) "+
        "or lower(p.customer.firstName) like lower(concat('%', :name,'%')) "+
        "or lower(p.pet.name) like lower(concat('%', :name,'%'))";
    String dateRestriction = "(p.date >= :startDate and p.date <= :endDate)";
    String orderClause = "order by p.date desc";

    @Query("select p from Purchase p " +
        "where (" + nameRestriction + ") " + orderClause)
    List<Purchase> findByName(String name);

    @Query("select p from Purchase p " +
        "where (" + dateRestriction + ") " + orderClause)
    List<Purchase> findByDate(LocalDateTime startDate, LocalDateTime endDate);

    @Query("select p from Purchase p " +
        "where (" + nameRestriction + ") and (" + dateRestriction + ") " + orderClause)
    List<Purchase> findByNameAndDate(String name, LocalDateTime startDate, LocalDateTime endDate);

    @Query("select p from Purchase p " + orderClause)
    List<Purchase> findAllOrdered();

    default List<Purchase> filter(String name, LocalDate startDate, LocalDate endDate) {
        if(!StringUtils.hasText(name) && startDate==null && endDate==null) {
            return findAllOrdered();
        }
        if(startDate==null && endDate==null && StringUtils.hasText(name)) {
            return findByName(name);
        }
        LocalDateTime startDateTime=startDate==null?LocalDateTime.MIN:startDate.atStartOfDay();
        LocalDateTime endDateTime=endDate==null?LocalDateTime.now():endDate.atTime(23,59,59);
        if(!StringUtils.hasText(name)) {
            return findByDate(startDateTime, endDateTime);
        }
        return findByNameAndDate(name, startDateTime, endDateTime);
    }
}
