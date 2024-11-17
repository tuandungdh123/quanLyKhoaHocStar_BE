package com.example.coursemanagement.service;

import com.example.coursemanagement.data.DTO.EnrollmentDTO;

import java.util.List;
import java.util.Map;

public interface EnrollmentService {
    List<EnrollmentDTO> getAllEnrollments();

    EnrollmentDTO updateEnrollmentStatus(EnrollmentDTO enrollmentDTO);

    EnrollmentDTO getEnrollmentById(Integer enrollmentId);

    List<EnrollmentDTO> getEnrollmentsByCourseId(Integer courseId);

    EnrollmentDTO addEnrollment(EnrollmentDTO enrollmentDTO);

    List<EnrollmentDTO> getAllEnrollmentsByUserId(Integer userId);

    Map<String, Long> getEnrollmentStatisticsByCoursePrice();

    Map<String, Double> getMonthlyRevenueStatistics();
}
