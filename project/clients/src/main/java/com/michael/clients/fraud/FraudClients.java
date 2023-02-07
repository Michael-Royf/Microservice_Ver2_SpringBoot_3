package com.michael.clients.fraud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient("fraud")
public interface FraudClients {
    @GetMapping("api/v1/fraud-check/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    FraudCheckResponse fraudCheckResponseIsFraudster(@PathVariable("customerId") Long customerId);
}
