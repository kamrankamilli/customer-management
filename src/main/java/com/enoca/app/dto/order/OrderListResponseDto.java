package com.enoca.app.dto.order;

import java.util.List;

public record OrderListResponseDto(List<OrderResponseDto> orders,
                                   int pageNo,
                                   int pageSize,
                                   int totalElements,
                                   int totalPages,
                                   boolean isLast) {
}
