package com.enoca.app.service;

import com.enoca.app.dto.customer.CustomerDetailedResponseDto;
import com.enoca.app.dto.customer.CustomerListResponseDto;
import com.enoca.app.dto.customer.CustomerRequestDto;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CustomerService {

    /**
     * Gets list of the customers from system using pagination and sorting
     *
     * @param pageable pageable interface
     * @return list customer
     */
    CustomerListResponseDto getCustomers(Pageable pageable);

    /**
     * Gets list of the customers from system using pagination and sorting and name search
     *
     * @param searchName any value for search name
     * @param pageable pageable interface
     * @return list customer
     */
    CustomerListResponseDto getCustomers(String searchName, Pageable pageable);

    /**
     * Get customers without order
     * @param pageable pageable interface
     * @return list customer
     */
    CustomerListResponseDto getCustomersWithoutOrders(Pageable pageable);

    /**
     * Get single customer
     *
     * @param customerId customer id
     * @return customer
     */
    CustomerDetailedResponseDto getCustomer(UUID customerId);
    /**
     * Creates customer
     *
     * @param customerRequestDto customer object
     * @return created customer
     */
    CustomerDetailedResponseDto createCustomer(CustomerRequestDto customerRequestDto);

    /**
     * Updates customer
     *
     * @param customerId  customer id
     * @param customerRequestDto customer object
     * @return update customer
     */
    CustomerDetailedResponseDto updateCustomer(UUID customerId, CustomerRequestDto customerRequestDto);

    /**
     * Deletes customer form the system
     *
     * @param customerId customer id
     */
    void deleteCustomer(UUID customerId);

}
