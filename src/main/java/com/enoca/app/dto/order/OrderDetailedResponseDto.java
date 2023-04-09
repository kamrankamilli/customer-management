package com.enoca.app.dto.order;

import com.enoca.app.dto.customer.CustomerResponseDto;

import java.time.LocalDate;
import java.util.UUID;

public record OrderDetailedResponseDto(UUID id, String totalPrice, CustomerResponseDto customer, LocalDate createdDate) {
}
