package com.example.coursemanagement.payment.data;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "payments")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer paymentId;

    @Column(name = "enrollment_id", nullable = false)
    private Integer enrollmentId;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "order_info", nullable = false, length = 255)
    private String orderInfo;

    @Column(name = "transaction_id", nullable = false, length = 100)
    private String transactionId;

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate = LocalDateTime.now();

    @Column(name = "payment_status", nullable = false, length = 50)
    private String paymentStatus;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
