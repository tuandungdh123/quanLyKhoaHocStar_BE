package com.example.coursemanagement.service;

import com.example.coursemanagement.data.DTO.EnrollmentDTO;
import com.example.coursemanagement.data.DTO.UpdateEnrollmentStatusDTO;

import java.util.List;
import java.util.Map;

public interface EnrollmentService {
    List<EnrollmentDTO> getAllEnrollments();
    UpdateEnrollmentStatusDTO updateEnrollmentStatus(UpdateEnrollmentStatusDTO updateEnrollmentStatusDTO);
    EnrollmentDTO getEnrollmentById(Integer enrollmentId);
    List<EnrollmentDTO> getEnrollmentsByCourseId(Integer courseId);
    EnrollmentDTO addEnrollment(EnrollmentDTO enrollmentDTO);
    List<EnrollmentDTO> getAllEnrollmentsByUserId(Integer userId);
    EnrollmentDTO checkEnrollment(Integer userId, Integer courseId);
    EnrollmentDTO getEnrollmentByUserIdAndCourseId(Integer userId, Integer courseId);
    EnrollmentDTO completeCourse(Integer enrollmentId, String certificateUrl);

}
