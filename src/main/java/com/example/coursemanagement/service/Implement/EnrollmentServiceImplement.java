package com.example.coursemanagement.service.Implement;

import com.example.coursemanagement.data.DTO.EnrollmentDTO;
import com.example.coursemanagement.data.entity.CourseEntity;
import com.example.coursemanagement.data.entity.EnrollmentEntity;
import com.example.coursemanagement.data.entity.UserEntity;
import com.example.coursemanagement.repository.CourseRepository;
import com.example.coursemanagement.repository.EnrollmentRepository;
import com.example.coursemanagement.repository.UserRepository;
import com.example.coursemanagement.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImplement implements EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;


    @Override
    public List<EnrollmentDTO> getAllEnrollments() {
        List<EnrollmentEntity> enrollment = enrollmentRepository.findAll();
        return enrollment.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public EnrollmentDTO updateEnrollmentStatus(Integer enrollmentId, EnrollmentDTO.EnrollmentStatus status, EnrollmentDTO.PaymentStatus paymentStatus) {
        EnrollmentEntity enrollmentEntity = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid enrollment ID"));

        // Cập nhật status và payment status
        enrollmentEntity.setStatus(EnrollmentEntity.EnrollmentStatus.valueOf(status.name()));
        enrollmentEntity.setPaymentStatus(EnrollmentEntity.PaymentStatus.valueOf(paymentStatus.name()));

        // Lưu lại bản ghi đã cập nhật
        EnrollmentEntity updatedEntity = enrollmentRepository.save(enrollmentEntity);

        return convertToDTO(updatedEntity);
    }

    public EnrollmentDTO convertToDTO(EnrollmentEntity enrollmentEntity) {
        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setEnrollmentId(enrollmentEntity.getEnrollmentId());
        dto.setUserId(enrollmentEntity.getUser().getUserId());
        dto.setCourseId(enrollmentEntity.getCourse().getCourseId());

        // Chuyển đổi giữa enum của Entity và DTO
        dto.setStatus(EnrollmentDTO.EnrollmentStatus.valueOf(enrollmentEntity.getStatus().name()));
        dto.setEnrollmentDate(enrollmentEntity.getEnrollmentDate());
        dto.setPaymentStatus(EnrollmentDTO.PaymentStatus.valueOf(enrollmentEntity.getPaymentStatus().name()));

        return dto;
    }

    public EnrollmentEntity convertToEntity(EnrollmentDTO enrollmentDTO) {
        EnrollmentEntity entity = new EnrollmentEntity();

        UserEntity user = userRepository.findById(enrollmentDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        CourseEntity course = courseRepository.findById(enrollmentDTO.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid course ID"));

        entity.setEnrollmentId(enrollmentDTO.getEnrollmentId());
        entity.setUser(user);
        entity.setCourse(course);

        // Chuyển đổi giữa enum của DTO và Entity
        entity.setStatus(EnrollmentEntity.EnrollmentStatus.valueOf(enrollmentDTO.getStatus().name()));
        entity.setEnrollmentDate(enrollmentDTO.getEnrollmentDate());
        entity.setPaymentStatus(EnrollmentEntity.PaymentStatus.valueOf(enrollmentDTO.getPaymentStatus().name()));

        return entity;
    }
}
