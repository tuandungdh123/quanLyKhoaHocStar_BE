package com.example.coursemanagement.payment.repository;

import com.example.coursemanagement.payment.data.PaymentEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentEntity, Integer> {
    PaymentEntity findByTransactionId(String transactionId);
//    boolean existsByEnrollmentId(Integer enrollmentId);
//    @Modifying
//    @Transactional
//    @Query("UPDATE PaymentEntity p SET p.paymentStatus = :paymentStatus WHERE p.transactionId = :transactionId")
//    void updatePaymentStatusByTransactionId(String paymentStatus, String transactionId);
}
