package com.enoca.app.dto.customer;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public record CustomerRequestDto(@NotEmpty String name, @NotNull @Min(1) int age) {
}
