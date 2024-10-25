package com.example.coursemanagement.service;

import com.example.coursemanagement.data.DTO.EnrollmentDTO;

import java.util.List;

public interface EnrollmentService {
    List<EnrollmentDTO> getAllEnrollments();
    EnrollmentDTO updateEnrollmentStatus(Integer enrollmentId, EnrollmentDTO.EnrollmentStatus status, EnrollmentDTO.PaymentStatus paymentStatus);
}
