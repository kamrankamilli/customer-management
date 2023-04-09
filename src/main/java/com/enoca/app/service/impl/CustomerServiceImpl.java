package com.enoca.app.service.impl;

import com.enoca.app.dto.customer.CustomerDetailedResponseDto;
import com.enoca.app.dto.customer.CustomerListResponseDto;
import com.enoca.app.dto.customer.CustomerRequestDto;
import com.enoca.app.dto.customer.CustomerResponseDto;
import com.enoca.app.exception.ResourceNotFoundException;
import com.enoca.app.mapper.CustomerMapper;
import com.enoca.app.model.Customer;
import com.enoca.app.repository.CustomerRepository;
import com.enoca.app.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    @Override
    public CustomerListResponseDto getCustomers(Pageable pageable) {
        Page<Customer> customerPage = customerRepository.findAll(pageable);

        List<CustomerResponseDto> customerList = customerPage.getContent()
                .stream()
                .map(CustomerMapper.MAPPER::mapToCustomerDto)
                .toList();
        return new CustomerListResponseDto(customerList,
                customerPage.getNumber()+1,
                customerPage.getSize(),
                (int) customerPage.getTotalElements(),
                customerPage.getTotalPages(),
                customerPage.isLast());
    }

    @Override
    public CustomerListResponseDto getCustomers(String searchName, Pageable pageable) {
        Page<Customer> customerPage = customerRepository.searchCustomer(searchName.toLowerCase(), pageable);

        List<CustomerDetailedResponseDto> customerList = customerPage.getContent()
                .stream()
                .map(CustomerMapper.MAPPER::mapToCustomerDetailedDto)
                .toList();
        return new CustomerListResponseDto(customerList,
                customerPage.getNumber()+1,
                customerPage.getSize(),
                (int) customerPage.getTotalElements(),
                customerPage.getTotalPages(),
                customerPage.isLast());
    }

    @Override
    public CustomerListResponseDto getCustomersWithoutOrders(Pageable pageable) {
        Page<Customer> customerPage = customerRepository.findAllByOrdersIsNull(pageable);
        List<CustomerResponseDto> customerList = customerPage.getContent()
                .stream()
                .map(CustomerMapper.MAPPER::mapToCustomerDto)
                .toList();
        return new CustomerListResponseDto(customerList,
                customerPage.getNumber()+1,
                customerPage.getSize(),
                (int) customerPage.getTotalElements(),
                customerPage.getTotalPages(),
                customerPage.isLast());
    }

    @Override
    public CustomerDetailedResponseDto getCustomer(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));
        return CustomerMapper.MAPPER.mapToCustomerDetailedDto(customer);
    }

    @Override
    public CustomerDetailedResponseDto createCustomer(CustomerRequestDto customerRequestDto) {
        Customer customer = CustomerMapper.MAPPER.mapToCustomer(customerRequestDto);
        Customer createdCustomer = customerRepository.saveAndFlush(customer);
        return CustomerMapper.MAPPER.mapToCustomerDetailedDto(createdCustomer);
    }

    @Override
    public CustomerDetailedResponseDto updateCustomer(UUID customerId, CustomerRequestDto customerRequestDto) {
        Customer existingCustomer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));
        existingCustomer.setName(customerRequestDto.name());
        existingCustomer.setAge(customerRequestDto.age());
        Customer updatedCustomer = customerRepository.saveAndFlush(existingCustomer);
        return CustomerMapper.MAPPER.mapToCustomerDetailedDto(updatedCustomer);
    }

    @Override
    public void deleteCustomer(UUID customerId) {
        customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));
        customerRepository.deleteById(customerId);
    }
}
