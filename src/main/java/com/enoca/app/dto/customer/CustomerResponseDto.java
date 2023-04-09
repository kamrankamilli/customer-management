package com.enoca.app.dto.customer;

import java.util.UUID;

public record CustomerResponseDto(UUID id, String name, int age) {
}
