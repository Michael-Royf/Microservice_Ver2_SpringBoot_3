package com.michael.customer.payload.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FraudCheckResponse {
    private Boolean isFraudster;
}
