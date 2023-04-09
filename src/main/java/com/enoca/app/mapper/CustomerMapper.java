package com.enoca.app.mapper;

import com.enoca.app.dto.customer.CustomerDetailedResponseDto;
import com.enoca.app.dto.customer.CustomerRequestDto;
import com.enoca.app.dto.customer.CustomerResponseDto;
import com.enoca.app.model.Customer;
import com.enoca.app.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper
public interface CustomerMapper {

    CustomerMapper MAPPER = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orders", ignore = true)
    Customer mapToCustomer(CustomerRequestDto customerRequestDto);

//    @Mapping(target = "orderIds",source = "orders",qualifiedByName = "mapOrdersToOrderIds")
    CustomerResponseDto mapToCustomerDto(Customer customer);
    CustomerDetailedResponseDto mapToCustomerDetailedDto(Customer customer);

    @Named("mapOrdersToOrderIds")
    default List<UUID> mapOrdersToOrderIds(List<Order> orders) {
        return orders.stream().map(Order::getId).collect(Collectors.toList());
    }
}
