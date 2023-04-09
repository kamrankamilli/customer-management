package com.enoca.app.controller;

import com.enoca.app.dto.customer.CustomerDetailedResponseDto;
import com.enoca.app.dto.customer.CustomerListResponseDto;
import com.enoca.app.dto.customer.CustomerRequestDto;
import com.enoca.app.service.CustomerService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
@AllArgsConstructor
@Tag(name = "Customer", description = "APIs for operation on Customers")
public class CustomerRestController {

    private final CustomerService customerService;

    @GetMapping
    @Operation(summary = "List All Customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customers retrieved", content = @Content(schema = @Schema(implementation = CustomerListResponseDto.class)))})
    public ResponseEntity<CustomerListResponseDto> getCustomers(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        CustomerListResponseDto customers = customerService.getCustomers(pageable);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/search")
    @Operation(summary = "List All Customers By Name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customers by name retrieved", content = @Content(schema = @Schema(implementation = CustomerListResponseDto.class)))})
    public ResponseEntity<CustomerListResponseDto> getCustomers(@RequestParam(value = "name") String name,
                                                                @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        CustomerListResponseDto customers = customerService.getCustomers(name, pageable);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/without-orders")
    @Operation(summary = "List All Customers Without Order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customers without retrieved", content = @Content(schema = @Schema(implementation = CustomerListResponseDto.class)))})
    public ResponseEntity<CustomerListResponseDto> getCustomersWithoutOrder(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        CustomerListResponseDto customers = customerService.getCustomersWithoutOrders(pageable);
        return ResponseEntity.ok(customers);
    }


    @GetMapping("/{customerId}")
    @Operation(summary = "Retrieve an Existing Customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer retrieved", content = @Content(schema = @Schema(implementation = CustomerDetailedResponseDto.class)))})
    public ResponseEntity<CustomerDetailedResponseDto> getCustomer(@PathVariable UUID customerId) {
        CustomerDetailedResponseDto customer = customerService.getCustomer(customerId);
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    @Operation(summary = "Create a New Customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created", content = @Content(schema = @Schema(implementation = CustomerDetailedResponseDto.class)))})
    public ResponseEntity<CustomerDetailedResponseDto> postCustomer(@Valid @RequestBody CustomerRequestDto customerRequestDto) {
        CustomerDetailedResponseDto createdCustomer = customerService.createCustomer(customerRequestDto);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    @PutMapping("/{customerId}")
    @Operation(summary = "Update an Existing Customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated", content = @Content(schema = @Schema(implementation = CustomerDetailedResponseDto.class)))})
    public ResponseEntity<CustomerDetailedResponseDto> putCustomer(@PathVariable UUID customerId, @Valid @RequestBody CustomerRequestDto customerRequestDto) {
        CustomerDetailedResponseDto updatedCustomer = customerService.updateCustomer(customerId, customerRequestDto);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{customerId}")
    @Operation(summary = "Delete a Customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content", content = @Content(schema = @Schema(hidden = true)))})
    public ResponseEntity<Object> deleteCustomer(@PathVariable UUID customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
