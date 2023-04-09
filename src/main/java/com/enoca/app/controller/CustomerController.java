package com.enoca.app.controller;

import com.enoca.app.dto.customer.CustomerDetailedResponseDto;
import com.enoca.app.dto.customer.CustomerListResponseDto;
import com.enoca.app.dto.customer.CustomerRequestDto;
import com.enoca.app.model.Customer;
import com.enoca.app.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public String getCustomers(@RequestParam(value = "name", required = false) String name,
                               @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                               Model model) {

        CustomerListResponseDto customers = null;
        if (name != null && !name.equals("")) {
            customers = customerService.getCustomers(name, pageable);
        } else {
            customers = customerService.getCustomers(pageable);
        }
        model.addAttribute("customers", customers);
        return "customer/list-customers";
    }

    @PostMapping("/create")
    public String createCustomer(@Valid @ModelAttribute("customer") CustomerRequestDto customerRequestDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("customer", customerRequestDto);
            model.addAttribute("update", false);
            return "customer/customer-form";
        }
        customerService.createCustomer(customerRequestDto);
        return "redirect:/customers";
    }

    @PostMapping("/{customerId}/update")
    public String updateCustomer(@PathVariable UUID customerId,
                                 @Valid @ModelAttribute("customer") CustomerRequestDto customerRequestDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("customerId",customerId);
            model.addAttribute("customer", customerRequestDto);
            model.addAttribute("update", true);
            return "customer/customer-form";
        }
        CustomerDetailedResponseDto updatedCustomer = customerService.updateCustomer(customerId, customerRequestDto);
        return "redirect:/customers";
    }


    @GetMapping("/add")
    public String createCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("update", false);
        return "customer/customer-form";
    }

    @GetMapping("/{customerId}/edit")
    public String updateCustomerForm(@PathVariable UUID customerId, Model model) {
        CustomerDetailedResponseDto customer = customerService.getCustomer(customerId);
        model.addAttribute("customer", customer);
        model.addAttribute("update", true);
        return "customer/customer-form";
    }

    @PostMapping("/{customerId}")
    public String deleteCustomer(@PathVariable UUID customerId) {
        customerService.deleteCustomer(customerId);
        return "redirect:/customers";
    }

    @GetMapping("/{customerId}")
    public String getCustomerDetails(@PathVariable UUID customerId, Model model) {
        CustomerDetailedResponseDto customer = customerService.getCustomer(customerId);
        model.addAttribute("customer", customer);
        return "customer/customer-details";
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
}
