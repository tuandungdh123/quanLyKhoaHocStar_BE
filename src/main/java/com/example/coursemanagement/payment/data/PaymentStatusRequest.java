package com.example.coursemanagement.payment.data;

import lombok.Data;

@Data
public class PaymentStatusRequest {
    private String transactionId;
    private String paymentStatus;
}
