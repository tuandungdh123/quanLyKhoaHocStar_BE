package com.example.coursemanagement.payment.repository;

import com.example.coursemanagement.payment.data.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentEntity, Integer> {
    PaymentEntity findByTransactionId(String transactionId);
}
