package com.example.coursemanagement.payment.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {
    private Integer paymentId;
    private Integer enrollmentId;
    private Integer amount;
    private String orderInfo;
    private String transactionId;
    private LocalDateTime paymentDate = Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime();
    private LocalDateTime updatedAt =Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime();
    private LocalDateTime createdAt = Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime();
    private String paymentStatus;
}
