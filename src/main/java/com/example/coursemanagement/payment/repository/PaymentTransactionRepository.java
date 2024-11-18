package com.example.coursemanagement.payment.repository;

import com.example.coursemanagement.payment.data.PaymentTransaction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE PaymentTransaction p SET p.paymentStatus = :paymentStatus WHERE p.transactionId = :transactionId")
    void updatePaymentStatusByTransactionId(String paymentStatus, String transactionId);
}
