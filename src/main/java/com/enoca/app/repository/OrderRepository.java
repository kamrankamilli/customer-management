package com.enoca.app.repository;

import com.enoca.app.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.UUID;


public interface OrderRepository extends JpaRepository<Order, UUID> {

    String GET_ORDERS_AFTER_DATE = "SELECT o FROM Order o WHERE o.createdDate > :createdDate";

    @Query(value = GET_ORDERS_AFTER_DATE)
    Page<Order> findOrdersCreatedAfter(@Param("createdDate") LocalDate createdDate, Pageable pageable);
}
