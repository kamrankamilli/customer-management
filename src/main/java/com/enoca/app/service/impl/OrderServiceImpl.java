package com.enoca.app.service.impl;

import com.enoca.app.dto.order.OrderDetailedResponseDto;
import com.enoca.app.dto.order.OrderListResponseDto;
import com.enoca.app.dto.order.OrderRequestDto;
import com.enoca.app.dto.order.OrderResponseDto;
import com.enoca.app.exception.ResourceNotFoundException;
import com.enoca.app.mapper.OrderMapper;
import com.enoca.app.model.Customer;
import com.enoca.app.model.Order;
import com.enoca.app.repository.CustomerRepository;
import com.enoca.app.repository.OrderRepository;
import com.enoca.app.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    @Override
    public OrderListResponseDto getOrders(Pageable pageable) {
        Page<Order> orderPage = orderRepository.findAll(pageable);

        List<OrderResponseDto> orders = orderPage.getContent()
                .stream()
                .map(OrderMapper.MAPPER::mapToOrderDto)
                .toList();

        return new OrderListResponseDto(orders, orderPage.getNumber()+1,
                orderPage.getSize(),
                (int) orderPage.getTotalElements(),
                orderPage.getTotalPages(),
                orderPage.isLast());
    }

    @Override
    public OrderListResponseDto getOrders(LocalDate date, Pageable pageable) {
        Page<Order> orderPage = orderRepository.findOrdersCreatedAfter(date, pageable);

        List<OrderResponseDto> orders = orderPage.getContent()
                .stream()
                .map(OrderMapper.MAPPER::mapToOrderDto)
                .toList();

        return new OrderListResponseDto(orders, orderPage.getNumber()+1,
                orderPage.getSize(),
                (int) orderPage.getTotalElements(),
                orderPage.getTotalPages(),
                orderPage.isLast());
    }

    @Override
    public OrderDetailedResponseDto getOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
        return OrderMapper.MAPPER.mapToOrderDetailedDto(order);
    }

    @Override
    public OrderDetailedResponseDto createOrder(OrderRequestDto orderRequestDto) {
        Customer customer = customerRepository.findById(orderRequestDto.customerId()).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", orderRequestDto.customerId()));
        Order order = OrderMapper.MAPPER.mapToOrder(orderRequestDto);
        order.setCustomer(customer);
        Order createdOrder = orderRepository.saveAndFlush(order);
        return OrderMapper.MAPPER.mapToOrderDetailedDto(createdOrder);
    }

    @Override
    public OrderDetailedResponseDto updateOrder(UUID orderId, OrderRequestDto orderRequestDto) {
        Order existingOrder = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
        existingOrder.setTotalPrice(orderRequestDto.totalPrice());
        Customer customer = customerRepository.findById(orderRequestDto.customerId()).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", orderRequestDto.customerId()));
        existingOrder.setCustomer(customer);
        Order updatedOrder = orderRepository.saveAndFlush(existingOrder);
        return OrderMapper.MAPPER.mapToOrderDetailedDto(updatedOrder);
    }

    @Override
    public void deleteOrder(UUID orderId) {
        orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
        orderRepository.deleteById(orderId);
    }
}
