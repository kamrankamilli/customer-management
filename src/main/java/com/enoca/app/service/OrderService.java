package com.enoca.app.service;

import com.enoca.app.dto.order.OrderDetailedResponseDto;
import com.enoca.app.dto.order.OrderListResponseDto;
import com.enoca.app.dto.order.OrderRequestDto;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.UUID;

public interface OrderService {

    /**
     * Gets list of orders from the system
     *
     * @param pageable pageable interface
     * @return order list dto
     */

    OrderListResponseDto getOrders(Pageable pageable);


    /**
     * Gets list of orders by after date from the system
     * @param pageable pageable interface
     * @return order list dto
     */

    OrderListResponseDto getOrders(LocalDate date, Pageable pageable);

    /**
     * Get single order in details
     *
     * @param orderId order id
     * @return order detailed dto
     */
    OrderDetailedResponseDto getOrder(UUID orderId);

    /**
     * Creates order
     *
     * @param orderRequestDto order request object
     * @return created order
     */
    OrderDetailedResponseDto createOrder(OrderRequestDto orderRequestDto);

    /**
     * Updates order
     *
     * @param orderId         order id
     * @param orderRequestDto order request object
     * @return updated order
     */
    OrderDetailedResponseDto updateOrder(UUID orderId, OrderRequestDto orderRequestDto);


    /**
     * Deletes order from the system
     *
     * @param orderId order Id
     */
    void deleteOrder(UUID orderId);
}
