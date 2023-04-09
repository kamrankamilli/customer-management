package com.enoca.app.dto.order;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OrderRequestDto(@NotNull @Digits(integer = 12, fraction = 2) @DecimalMin(value = "0.0") Double totalPrice,
                              @NotNull UUID customerId) {
}
