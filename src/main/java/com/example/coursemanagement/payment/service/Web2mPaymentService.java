//package com.example.coursemanagement.payment.service;
//
//import com.example.coursemanagement.payment.data.PaymentRequest;
//import com.example.coursemanagement.payment.data.PaymentResponse;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class Web2mPaymentService {
//
//    @Value("${web2m.api.key}")  // Lấy API key từ file cấu hình (application.properties)
//    private String apiKey;
//
//    private final String baseUrl = "https://api.web2m.com/";  // URL gốc của Web2m API
//
//    private final RestTemplate restTemplate = new RestTemplate();  // RestTemplate để gửi yêu cầu HTTP
//
//    // Phương thức xử lý thanh toán
//    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
//        // Endpoint thanh toán (thay đổi nếu cần theo tài liệu API)
//        String endpoint = "payment/checkout";
//
//        // Thêm thông tin header, bao gồm API key và Content-Type
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + apiKey);
//        headers.set("Content-Type", "application/json");
//
//        // Tạo đối tượng HttpEntity bao gồm yêu cầu thanh toán và header
//        HttpEntity<PaymentRequest> entity = new HttpEntity<>(paymentRequest, headers);
//
//        // Gửi yêu cầu POST đến API Web2m và nhận kết quả
//        ResponseEntity<PaymentResponse> responseEntity = restTemplate.exchange(
//                baseUrl + endpoint, HttpMethod.POST, entity, PaymentResponse.class);
//
//        // Trả về phản hồi từ Web2m, bao gồm URL thanh toán
//        PaymentResponse paymentResponse = responseEntity.getBody();
//
//        if (paymentResponse != null && paymentResponse.isSuccess()) {
//            // Nếu Web2m trả về URL thanh toán, có thể sử dụng cho frontend
//            String paymentUrl = paymentResponse.getPaymentUrl();
//            // Tạo mã QR hoặc redirect tới URL thanh toán
//        }
//
//        return paymentResponse;
//    }
//}
