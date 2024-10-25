package com.example.coursemanagement.repository;

import com.example.coursemanagement.data.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {
}
