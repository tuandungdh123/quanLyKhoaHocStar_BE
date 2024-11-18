//package com.example.coursemanagement.service.Implement;
//
//import com.example.coursemanagement.data.DTO.PaymentDTO;
//import com.example.coursemanagement.data.entity.EnrollmentEntity;
//import com.example.coursemanagement.data.entity.PaymentEntity;
//import com.example.coursemanagement.exception.AppException;
//import com.example.coursemanagement.exception.ErrorCode;
//import com.example.coursemanagement.payment.config.VNPayConfig;
//import com.example.coursemanagement.repository.PaymentRepository;
//import com.example.coursemanagement.service.PaymentService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class PaymentServiceImplement implements PaymentService {
//    private final PaymentRepository paymentRepository;
//
//    @Override
//    public List<PaymentDTO> getAllPayment() {
//        List<PaymentEntity> payments = paymentRepository.findAll();
//        return payments.stream().map(this::convertToDTO).collect(Collectors.toList());
//    }
//
//    @Override
//    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
//        PaymentEntity paymentEntity = convertToEntity(paymentDTO);
//        paymentRepository.save(paymentEntity);
//        return paymentDTO;
//    }
//
//    @Override
//    public void updatePaymentStatus(Integer paymentId, PaymentDTO.PaymentStatus status) {
//        PaymentEntity paymentEntity = paymentRepository.findById(paymentId)
//                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND, ErrorCode.PAYMENT_NOT_FOUND.getMessage()));
//
//        paymentEntity.setStatus(PaymentEntity.PaymentStatus.valueOf(status.name()));
//        paymentRepository.save(paymentEntity);
//    }
//
//    private PaymentDTO convertToDTO(PaymentEntity paymentEntity) {
//        PaymentDTO paymentDTO = new PaymentDTO();
//        paymentDTO.setPaymentId(paymentEntity.getPaymentId());
//        paymentDTO.setEnrollmentId(paymentEntity.getEnrollment().getEnrollmentId());
//        paymentDTO.setAmount(paymentEntity.getAmount());
//        paymentDTO.setPaymentDate(paymentEntity.getPaymentDate());
//        paymentDTO.setStatus(PaymentDTO.PaymentStatus.valueOf(paymentEntity.getStatus().name()));
//        paymentDTO.setTransactionId(paymentEntity.getTransactionId());
//        return paymentDTO;
//    }
//
//    private PaymentEntity convertToEntity(PaymentDTO paymentDTO) {
//        PaymentEntity paymentEntity = new PaymentEntity();
//        paymentEntity.setPaymentId(paymentDTO.getPaymentId());
//
//        EnrollmentEntity enrollment = new EnrollmentEntity();
//        enrollment.setEnrollmentId(paymentDTO.getEnrollmentId());
//        paymentEntity.setEnrollment(enrollment);
//
//        paymentEntity.setAmount(paymentDTO.getAmount());
//        paymentEntity.setPaymentDate(paymentDTO.getPaymentDate());
//        paymentEntity.setStatus(PaymentEntity.PaymentStatus.valueOf(paymentDTO.getStatus().name()));
//        paymentEntity.setTransactionId(paymentDTO.getTransactionId());
//
//        return paymentEntity;
//    }
//
//    public String createOrder(Double total, String orderInfor, String urlReturn){
//        String vnp_Version = "2.1.0";
//        String vnp_Command = "pay";
//        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
//        String vnp_IpAddr = "127.0.0.1";
//        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
//        String orderType = "order-type";
//
//        Map<String, String> vnp_Params = new HashMap<>();
//        vnp_Params.put("vnp_Version", vnp_Version);
//        vnp_Params.put("vnp_Command", vnp_Command);
//        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
//        vnp_Params.put("vnp_Amount", String.valueOf(total*100));
//        vnp_Params.put("vnp_CurrCode", "VND");
//
//        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
//        vnp_Params.put("vnp_OrderInfo", orderInfor);
//        vnp_Params.put("vnp_OrderType", orderType);
//
//        String locate = "vn";
//        vnp_Params.put("vnp_Locale", locate);
//
//        urlReturn += VNPayConfig.vnp_ReturnUrl;
//        vnp_Params.put("vnp_ReturnUrl", urlReturn);
//        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
//
//        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//        String vnp_CreateDate = formatter.format(cld.getTime());
//        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
//
//        cld.add(Calendar.MINUTE, 15);
//        String vnp_ExpireDate = formatter.format(cld.getTime());
//        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
//
//        List fieldNames = new ArrayList(vnp_Params.keySet());
//        Collections.sort(fieldNames);
//        StringBuilder hashData = new StringBuilder();
//        StringBuilder query = new StringBuilder();
//        Iterator itr = fieldNames.iterator();
//        while (itr.hasNext()) {
//            String fieldName = (String) itr.next();
//            String fieldValue = (String) vnp_Params.get(fieldName);
//            if ((fieldValue != null) && (fieldValue.length() > 0)) {
//                //Build hash data
//                hashData.append(fieldName);
//                hashData.append('=');
//                try {
//                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
//                    //Build query
//                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
//                    query.append('=');
//                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//                if (itr.hasNext()) {
//                    query.append('&');
//                    hashData.append('&');
//                }
//            }
//        }
//        String queryUrl = query.toString();
//        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());
//        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
//        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;
//        return paymentUrl;
//    }
//
//}
