package com.example.coursemanagement.service;

import com.example.coursemanagement.data.DTO.EnrollmentDTO;

import java.util.List;
import java.util.Optional;

public interface EnrollmentService {
    List<EnrollmentDTO> getAllEnrollments();

    EnrollmentDTO updateEnrollmentStatus(EnrollmentDTO enrollmentDTO);

    EnrollmentDTO getEnrollmentById(Integer enrollmentId);

    Optional<EnrollmentDTO> getEnrollmentsByCourseId(Integer courseId);

    EnrollmentDTO addEnrollment(EnrollmentDTO enrollmentDTO);
}
