package com.michael.clients.notification;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NotificationRequest {
    private Long toCustomerId;
    private String toCustomerName;
    private String message;
}
