package com.enoca.app.dto.customer;

import java.util.List;

public record CustomerListResponseDto(List<?> customers,
                                      int pageNo,
                                      int pageSize,
                                      int totalElements,
                                      int totalPages,
                                      boolean isLast) {
}
