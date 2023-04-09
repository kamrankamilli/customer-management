package com.enoca.app.dto.customer;

import com.enoca.app.dto.order.OrderResponseDto;

import java.util.List;
import java.util.UUID;

public record CustomerDetailedResponseDto(UUID id, String name, int age, List<OrderResponseDto> orders) {
}
