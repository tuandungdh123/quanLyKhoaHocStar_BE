package com.example.coursemanagement.payment.data;

import lombok.Data;

@Data
public class OrderRequest {
    private Integer amount;
    private String orderInfo;
    private Integer EnrollmentId;
}
