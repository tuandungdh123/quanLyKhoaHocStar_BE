package com.example.coursemanagement.service;

import com.example.coursemanagement.data.DTO.PaymentDTO;

import java.util.List;

public interface PaymentService {
    List<PaymentDTO> getAllPayment();

    PaymentDTO createPayment(PaymentDTO paymentDTO);

    void updatePaymentStatus(Integer paymentId, PaymentDTO.PaymentStatus status);
}
