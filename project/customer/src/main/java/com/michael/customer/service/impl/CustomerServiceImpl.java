package com.michael.customer.service.impl;

import com.michael.clients.fraud.FraudCheckResponse;
import com.michael.clients.fraud.FraudClients;
import com.michael.clients.notification.NotificationClient;
import com.michael.clients.notification.NotificationRequest;
import com.michael.customer.entity.Customer;
import com.michael.customer.payload.request.CustomerRequest;
import com.michael.customer.payload.response.CustomerResponse;
import com.michael.customer.repository.CustomerRepository;
import com.michael.customer.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private FraudClients fraudClients;

    @Autowired
    private NotificationClient notificationClient;

//    @Autowired
//    private RestTemplate restTemplate;

    @Override
    public CustomerResponse registerCustomer(CustomerRequest customerRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .email(customerRequest.getEmail())
                .build();
        customer = customerRepository.saveAndFlush(customer);

//        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
//                "http://FRAUD/api/v1/fraud-check/{customerId}",
//                FraudCheckResponse.class,
//                customer.getId()
//        );

        FraudCheckResponse fraudCheckResponse = fraudClients.fraudCheckResponseIsFraudster(customer.getId());

        if (fraudCheckResponse.getIsFraudster()) {
            throw new IllegalStateException("This customer is fraudster");
        }

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .toCustomerId(customer.getId())
                .toCustomerName(customer.getFirstName())
                .message(String.format("Hi %s, welcome to Michael WebSite...",
                        customer.getFirstName()))
                .build();
       notificationClient.sendNotification(notificationRequest);

        return mapper.map(customer, CustomerResponse.class);
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> mapper.map(customer, CustomerResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponse getCustomerById(Long customerId) {
        Customer customer = getCustomerByIdFromDb(customerId);
        return mapper.map(customer, CustomerResponse.class);

    }

    @Override
    public CustomerResponse updateCustomer(Long customerId, CustomerRequest customerRequest) {
        Customer customer = getCustomerByIdFromDb(customerId);
        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setEmail(customerRequest.getEmail());
        customer = customerRepository.save(customer);
        return mapper.map(customer, CustomerResponse.class);
    }

    @Override
    public String deleteCustomer(Long customerId) {
        Customer customer = getCustomerByIdFromDb(customerId);
        customerRepository.delete(customer);
        return String.format("Customer with id: %s was deleted", customerId);
    }

    private Customer getCustomerByIdFromDb(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Customer with id: %s not found", customerId)));
    }
}
