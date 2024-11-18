package com.example.coursemanagement.payment.api;

import com.example.coursemanagement.data.mgt.ResponseObject;
import com.example.coursemanagement.payment.data.OrderRequest;
import com.example.coursemanagement.payment.data.PaymentDTO;
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

//    @PostMapping("/savePayment")
//    public ResponseObject<?> addPayment(@RequestBody PaymentDTO paymentDTO) {
//        var resultApi = new ResponseObject<>();
//        try {
//            PaymentDTO newPayment = vnPayService.savePayment(paymentDTO);
//            resultApi.setData(newPayment);
//            resultApi.setSuccess(true);
//            resultApi.setMessage("Payment added successfully");
//        } catch (Exception e) {
//            resultApi.setSuccess(false);
//            resultApi.setMessage(e.getMessage());
//            log.error("Failed to add payment", e);
//        }
//        return resultApi;
//    }

    @PostMapping("/submitOrder")
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest orderRequest) {
        // Kiểm tra dữ liệu
        if (orderRequest.getAmount() <= 0 || orderRequest.getOrderInfo().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thiếu dữ liệu.");
        }

        // Gọi dịch vụ để tạo URL thanh toán
        String paymentUrl = vnPayService.createOrder(orderRequest.getAmount(), orderRequest.getOrderInfo(), "http://localhost:3000", orderRequest.getEnrollmentId());

        return ResponseEntity.ok(paymentUrl);
    }

    @GetMapping("/payment-return")
    public String handlePaymentReturn(HttpServletRequest request) {
        // In ra các tham số từ VNPay để kiểm tra
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            System.out.println(paramName + ": " + request.getParameter(paramName));
        }

        // Gọi dịch vụ xử lý trả về và trả kết quả
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
        // Gọi service để cập nhật trạng thái thanh toán
        return vnPayService.updatePaymentStatus(paymentStatusRequest.getTransactionId(), paymentStatusRequest.getPaymentStatus());
    }
}
