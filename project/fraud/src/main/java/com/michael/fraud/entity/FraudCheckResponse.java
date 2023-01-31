package com.michael.fraud.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FraudCheckResponse {
    private Boolean isFraudster;
}
