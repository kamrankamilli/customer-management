package com.enoca.app.mapper;


import com.enoca.app.dto.order.OrderDetailedResponseDto;
import com.enoca.app.dto.order.OrderRequestDto;
import com.enoca.app.dto.order.OrderResponseDto;
import com.enoca.app.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {
    OrderMapper MAPPER = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer",ignore = true)
    Order mapToOrder(OrderRequestDto orderRequestDto);

    @Mapping(target = "totalPrice",source = "totalPrice",qualifiedByName = "convertTotalPriceToString")
    OrderResponseDto mapToOrderDto(Order order);

    @Mapping(target = "totalPrice",source = "totalPrice",qualifiedByName = "convertTotalPriceToString")
    OrderDetailedResponseDto mapToOrderDetailedDto(Order order);

    @Named("convertTotalPriceToString")
    default String convertTotalPriceToString(Double totalPrice) {
        return String.format("%.2f", totalPrice);
    }
}
