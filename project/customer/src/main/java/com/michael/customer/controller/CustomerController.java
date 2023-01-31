package com.michael.customer.controller;

import com.michael.customer.payload.request.CustomerRequest;
import com.michael.customer.payload.response.CustomerResponse;
import com.michael.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/customers")
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @PostMapping
    public ResponseEntity<CustomerResponse> registerCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        return new ResponseEntity<>(customerService.registerCustomer(customerRequest), CREATED);
    }


    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomer() {
        return new ResponseEntity<>(customerService.getAllCustomers(), OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long customerId) {
        return new ResponseEntity<>(customerService.getCustomerById(customerId), OK);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable Long customerId) {
        return new ResponseEntity<>(customerService.deleteCustomer(customerId), OK);
    }


    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable Long customerId,
            @Valid @RequestBody CustomerRequest customerRequest
    ) {
        return new ResponseEntity<>(customerService.updateCustomer(customerId, customerRequest), OK);
    }

}
