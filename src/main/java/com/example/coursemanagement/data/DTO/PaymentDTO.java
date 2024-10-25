package com.example.coursemanagement.data.DTO;

import com.example.coursemanagement.data.Enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {
    private Integer paymentId;
    private Integer enrollmentId;  // Chỉ cần ID thay vì toàn bộ EnrollmentEntity
    private Double amount;
    private LocalDateTime paymentDate;
    private PaymentStatus status;
    private String transactionId;

    public enum PaymentStatus {
        pending,
        completed,
        failed
    }
}
