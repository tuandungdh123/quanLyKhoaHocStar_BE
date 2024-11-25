package com.example.coursemanagement.payment.api;

import com.example.coursemanagement.payment.data.OrderRequest;
import com.example.coursemanagement.payment.data.PaymentStatusRequest;
import com.example.coursemanagement.payment.service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;

@Slf4j
@RestController
@RequestMapping("/api/v1/vnpay")
@CrossOrigin(origins = "*")
public class VNPayApi {

    @Autowired
    private VNPayService vnPayService;

    @PostMapping("/submitOrder")
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest orderRequest) {
        if (orderRequest.getAmount() <= 0 || orderRequest.getOrderInfo().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thiếu dữ liệu.");
        }

        String paymentUrl = vnPayService.createOrder(orderRequest.getAmount(), orderRequest.getOrderInfo(), "http://localhost:3000", orderRequest.getEnrollmentId());

        return ResponseEntity.ok(paymentUrl);
    }

    @GetMapping("/payment-return")
    public String handlePaymentReturn(HttpServletRequest request) {
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            System.out.println(paramName + ": " + request.getParameter(paramName));
        }

        int result = vnPayService.orderReturn(request);
        return switch (result) {
            case 1 -> "Payment successful";
            case 0 -> "Payment failed";
            case -1 -> "Invalid payment signature";
            default -> "Unknown error";
        };
    }

    @PostMapping("/update-status")
    public String updatePaymentStatus(@RequestBody PaymentStatusRequest paymentStatusRequest) {
        return vnPayService.updatePaymentStatus(paymentStatusRequest.getTransactionId(), paymentStatusRequest.getPaymentStatus());
    }

    @PostMapping("/send-payment-success-email")
    public String sendPaymentSuccessEmail(@RequestBody PaymentStatusRequest paymentStatusRequest) {
        return vnPayService.sendPaymentSuccessEmail(paymentStatusRequest.getTransactionId(), paymentStatusRequest.getPaymentStatus());
    }
}
