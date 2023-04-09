package com.enoca.app.repository;

import com.enoca.app.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    String SEARCH_CUSTOMERS = "SELECT c FROM Customer c WHERE LOWER(c.name) LIKE %:name%";
    //String GET_CUSTOMERS_WITHOUT_ORDERS = "SELECT c FROM Customer c LEFT JOIN c.orders o WHERE o.id IS NULL";

    @Query(value = SEARCH_CUSTOMERS)
    Page<Customer> searchCustomer(@Param("name") String name, Pageable pageable);

//    @Query(value = GET_CUSTOMERS_WITHOUT_ORDERS)
//    Page<Customer> findCustomersWithoutOrders();

    Page<Customer> findAllByOrdersIsNull(Pageable pageable);
}
