package com.michael.fraud.controller;

import com.michael.clients.fraud.FraudCheckResponse;
import com.michael.fraud.service.FraudCheckHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fraud-check")
@Slf4j
public class FraudController {

    @Autowired
    private FraudCheckHistoryService fraudCheckHistoryService;

    @GetMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public FraudCheckResponse fraudCheckResponseIsFraudster(@PathVariable Long customerId) {
        boolean isFraudulentCustomer = fraudCheckHistoryService.isFraudulentCustomer(customerId);
        log.info("fraud check request for customer {}", customerId);
        return new FraudCheckResponse(isFraudulentCustomer);
    }


}
