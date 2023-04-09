package com.enoca.app.dto.order;

import java.time.LocalDate;
import java.util.UUID;

public record OrderResponseDto(UUID id, String totalPrice, LocalDate createdDate) {
}
