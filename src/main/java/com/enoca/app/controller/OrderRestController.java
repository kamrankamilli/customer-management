package com.enoca.app.controller;

import com.enoca.app.dto.order.OrderDetailedResponseDto;
import com.enoca.app.dto.order.OrderListResponseDto;
import com.enoca.app.dto.order.OrderRequestDto;
import com.enoca.app.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
@Tag(name = "Order", description = "APIs for operation on Orders")
public class OrderRestController {

    private final OrderService orderService;

    @GetMapping
    @Operation(summary = "List All Orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders retrieved", content = @Content(schema = @Schema(implementation = OrderListResponseDto.class)))})
    public ResponseEntity<OrderListResponseDto> getOrders(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        OrderListResponseDto orders = orderService.getOrders(pageable);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/search")
    @Operation(summary = "List All Orders By After Created Date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders after created date retrieved", content = @Content(schema = @Schema(implementation = OrderListResponseDto.class)))})
    public ResponseEntity<OrderListResponseDto> getOrders(@RequestParam(value = "createdDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate createdDate,
                                                          @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        OrderListResponseDto orders = orderService.getOrders(createdDate, pageable);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Retrieve an Existing Order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order retrieved", content = @Content(schema = @Schema(implementation = OrderListResponseDto.class)))})
    public ResponseEntity<OrderDetailedResponseDto> getOrder(@PathVariable UUID orderId) {
        final OrderDetailedResponseDto order = orderService.getOrder(orderId);
        return ResponseEntity.ok(order);
    }

    @PostMapping
    @Operation(summary = "Create new Order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created", content = @Content(schema = @Schema(implementation = OrderDetailedResponseDto.class)))})
    public ResponseEntity<OrderDetailedResponseDto> postOrder(@Valid @RequestBody OrderRequestDto orderRequestDto) {
        final OrderDetailedResponseDto createdOrder = orderService.createOrder(orderRequestDto);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PutMapping("/{orderId}")
    @Operation(summary = "Update an Existing Order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order updated", content = @Content(schema = @Schema(implementation = OrderDetailedResponseDto.class)))})
    public ResponseEntity<OrderDetailedResponseDto> putOrder(@PathVariable UUID orderId, @Valid @RequestBody OrderRequestDto orderRequestDto) {
        final OrderDetailedResponseDto updatedOrder = orderService.updateOrder(orderId, orderRequestDto);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{orderId}")
    @Operation(summary = "Delete an Order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content", content = @Content(schema = @Schema(hidden = true)))})
    public ResponseEntity<Object> deleteOrder(@PathVariable UUID orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
