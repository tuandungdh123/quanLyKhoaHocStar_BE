package com.example.coursemanagement.payment.service.impl;

import com.example.coursemanagement.data.entity.EnrollmentEntity;
import com.example.coursemanagement.payment.config.VNPayConfig;
import com.example.coursemanagement.payment.data.PaymentEntity;
import com.example.coursemanagement.payment.repository.PaymentTransactionRepository;
import com.example.coursemanagement.payment.service.VNPayService;
import com.example.coursemanagement.repository.EnrollmentRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class VNPayServiceImpl implements VNPayService {

    @Autowired
    private PaymentTransactionRepository paymentTransactionRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Override
    public String createOrder(int total, String orderInfo, String urlReturn, Integer enrollmentId) {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
        String orderType = "order-type";

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(total * 100));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", orderInfo);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = "vn";
        vnp_Params.put("vnp_Locale", locate);

        urlReturn += VNPayConfig.vnp_Returnurl;
        vnp_Params.put("vnp_ReturnUrl", urlReturn);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        // Tạo ngày giờ tạo đơn hàng
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        // Thêm thời gian hết hạn đơn hàng (15 phút)
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        // Tạo query string và hash data
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && fieldValue.length() > 0) {
                // Build hash data
                hashData.append(fieldName).append('=');
                try {
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    // Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()))
                            .append('=')
                            .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;
        System.out.println("URL thanh toán: " + paymentUrl);

        // Lưu thông tin giao dịch vào cơ sở dữ liệu
        PaymentEntity transaction = new PaymentEntity();
        transaction.setEnrollmentId(enrollmentId);
        transaction.setOrderInfo(orderInfo);
        transaction.setAmount(total);
        transaction.setTransactionId(vnp_TxnRef);
        transaction.setPaymentStatus("pending");
        paymentTransactionRepository.save(transaction);

        return paymentUrl;
    }

    @Override
    public int orderReturn(HttpServletRequest request) {
        Map<String, String> fields = new HashMap<>();
        for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements(); ) {
            String fieldName = params.nextElement();
            try {
                String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
                fields.put(fieldName, fieldValue);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        fields.remove("vnp_SecureHash");
        String signValue = VNPayConfig.hashAllFields(fields);
        String transactionId = request.getParameter("vnp_TxnRef");
        if (signValue.equals(vnp_SecureHash)) {
            if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
                updatePaymentStatus(transactionId, "SUCCESS");
                return 1; // Thành công
            } else {
                updatePaymentStatus(transactionId, "FAILED");
                return 0; // Thất bại
            }
        } else {
            updatePaymentStatus(transactionId, "FAILED");
            return -1; // Lỗi chữ ký
        }
    }

    public String updatePaymentStatus(String transactionId, String paymentStatus) {
        // Tìm giao dịch theo transactionId
        PaymentEntity paymentEntity = paymentTransactionRepository.findByTransactionId(transactionId);

        if (paymentEntity == null) {
            return "Không tìm thấy giao dịch với mã giao dịch " + transactionId;
        }

        // Cập nhật paymentStatus và updatedAt
        paymentEntity.setPaymentStatus(paymentStatus);
        paymentEntity.setUpdatedAt(LocalDateTime.now());
        paymentTransactionRepository.save(paymentEntity);

        EnrollmentEntity enrollmentEntity = enrollmentRepository.findByEnrollmentId(paymentEntity.getEnrollmentId());
        if (enrollmentEntity == null) {
            return "Không tìm thấy enrollment với enrollmentId " + paymentEntity.getEnrollmentId();
        }
        // Cập nhật paymentStatus cho bảng Enrollments
        enrollmentEntity.setPaymentStatus(EnrollmentEntity.PaymentStatus.valueOf(paymentStatus));
        enrollmentRepository.save(enrollmentEntity);

        return "Cập nhật trạng thái thanh toán thành công!";
    }
}

