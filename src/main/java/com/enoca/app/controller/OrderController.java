package com.enoca.app.controller;

import com.enoca.app.dto.customer.CustomerListResponseDto;
import com.enoca.app.dto.order.OrderDetailedResponseDto;
import com.enoca.app.dto.order.OrderListResponseDto;
import com.enoca.app.dto.order.OrderRequestDto;
import com.enoca.app.model.Order;
import com.enoca.app.service.CustomerService;
import com.enoca.app.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@Controller
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;

    @GetMapping
    public String getOrders(@RequestParam(value = "createdDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate createdDate,
                            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                            Model model) {

        OrderListResponseDto orders = null;
        if (createdDate != null) {
            orders = orderService.getOrders(createdDate, pageable);
        } else {
            orders = orderService.getOrders(pageable);
        }
        model.addAttribute("orders", orders);
        return "order/list-orders";
    }

    @GetMapping("/add")
    public String createOrderForm(Model model) {
        model.addAttribute("order", new Order());
        CustomerListResponseDto customers = loadCustomers();
        model.addAttribute("customers", customers.customers());
        model.addAttribute("update", false);
        return "order/order-form";
    }

    @GetMapping("/{orderId}/edit")
    public String updateCustomerForm(@PathVariable UUID orderId, Model model) {
        OrderDetailedResponseDto order = orderService.getOrder(orderId);
        CustomerListResponseDto customers = loadCustomers();
        model.addAttribute("customers", customers.customers());
        model.addAttribute("order", order);
        model.addAttribute("update", true);
        return "order/order-form";
    }

    @PostMapping("/create")
    public String createOrder(@Valid @ModelAttribute("order") OrderRequestDto orderRequestDto, BindingResult bindingResult,Model model) {
        if (bindingResult.hasErrors()) {
            CustomerListResponseDto customers = loadCustomers();
            model.addAttribute("customers", customers);
            model.addAttribute("update",false);
            return "order/order-form";
        }
        orderService.createOrder(orderRequestDto);
        return "redirect:/orders";
    }

    @PostMapping("/{orderId}/update")
    public String updateOrder(@PathVariable UUID orderId, @Valid @ModelAttribute("order") OrderRequestDto orderRequestDto, BindingResult bindingResult,Model model) {
        if (bindingResult.hasErrors()) {
            OrderDetailedResponseDto order = orderService.getOrder(orderId);
            CustomerListResponseDto customers = loadCustomers();
            model.addAttribute("customers", customers.customers());
            model.addAttribute("customerId",order.customer().id());
            model.addAttribute("orderId",orderId);
            model.addAttribute("order",orderRequestDto);
            model.addAttribute("update",true);
            return "order/order-form";
        }
        orderService.updateOrder(orderId, orderRequestDto);
        return "redirect:/orders";
    }

    @PostMapping("/{orderId}")
    public String deleteOrder(@PathVariable UUID orderId) {
        orderService.deleteOrder(orderId);
        return "redirect:/orders";
    }

    @GetMapping("/{orderId}")
    public String getCustomerDetails(@PathVariable UUID orderId, Model model) {
        OrderDetailedResponseDto order = orderService.getOrder(orderId);
        model.addAttribute("order", order);
        return "order/order-details";
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    private CustomerListResponseDto loadCustomers(){
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.by("name"));
        return customerService.getCustomers(pageable);
    }
}
