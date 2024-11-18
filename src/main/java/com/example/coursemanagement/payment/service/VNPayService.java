package com.example.coursemanagement.payment.service;

import jakarta.servlet.http.HttpServletRequest;

public interface VNPayService {
    String createOrder(int total, String orderInfor, String urlReturn, Integer enrollmentId);

    int orderReturn(HttpServletRequest request);

    String updatePaymentStatus(String transactionId, String paymentStatus);

}
