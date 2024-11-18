package com.example.coursemanagement.payment.service;

import com.example.coursemanagement.payment.data.PaymentDTO;
import com.example.coursemanagement.payment.data.PaymentEntity;
import jakarta.servlet.http.HttpServletRequest;

public interface VNPayService {
    String createOrder(int total, String orderInfor, String urlReturn, Integer enrollmentId);

    int orderReturn(HttpServletRequest request);

    String updatePaymentStatus(String transactionId, String paymentStatus);

//    boolean updatePaymentStatusForEnrollment(String transactionId, String paymentStatus);

//    PaymentDTO savePayment(PaymentDTO paymentDTO);
}
