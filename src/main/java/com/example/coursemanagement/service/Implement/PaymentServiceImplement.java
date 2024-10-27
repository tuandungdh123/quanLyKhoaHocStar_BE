package com.example.coursemanagement.service.Implement;


import com.example.coursemanagement.data.DTO.PaymentDTO;
import com.example.coursemanagement.data.entity.EnrollmentEntity;
import com.example.coursemanagement.data.entity.PaymentEntity;
import com.example.coursemanagement.repository.PaymentRepository;
import com.example.coursemanagement.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImplement implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Override
    public List<PaymentDTO> getAllPayment(){
        List<PaymentEntity> courses = paymentRepository.findAll();
        return courses.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        PaymentEntity paymentEntity = convertToEntity(paymentDTO);  // Convert từ DTO sang Entity
        paymentRepository.save(paymentEntity);  // Lưu vào database
        return paymentDTO;
    }

    @Override
    public void updatePaymentStatus(Integer paymentId, PaymentDTO.PaymentStatus status) {
        PaymentEntity paymentEntity = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        paymentEntity.setStatus(PaymentEntity.PaymentStatus.valueOf(status.name()));

        paymentRepository.save(paymentEntity);
    }


    private PaymentDTO convertToDTO(PaymentEntity paymentEntity) {
       PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPaymentId(paymentEntity.getPaymentId());
        paymentDTO.setEnrollmentId(paymentEntity.getEnrollment().getEnrollmentId()) ; // Lấy ID từ enrollment entity
        paymentDTO.setAmount(paymentEntity.getAmount());
        paymentDTO.setPaymentDate(paymentEntity.getPaymentDate());
        paymentDTO.setStatus(PaymentDTO.PaymentStatus.valueOf(paymentEntity.getStatus().name()));
        paymentDTO.setTransactionId(paymentEntity.getTransactionId());
        return paymentDTO;
    }

    private PaymentEntity convertToEntity(PaymentDTO paymentDTO) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setPaymentId(paymentDTO.getPaymentId());
        EnrollmentEntity enrollment = new EnrollmentEntity();
        enrollment.setEnrollmentId(paymentDTO.getEnrollmentId());
        paymentEntity.setEnrollment(enrollment);
        paymentEntity.setAmount(paymentDTO.getAmount());
        paymentEntity.setPaymentDate(paymentDTO.getPaymentDate());
        paymentEntity.setStatus(PaymentEntity.PaymentStatus.valueOf(paymentDTO.getStatus().name()));
        paymentEntity.setTransactionId(paymentDTO.getTransactionId());

        return paymentEntity;
    }
}
