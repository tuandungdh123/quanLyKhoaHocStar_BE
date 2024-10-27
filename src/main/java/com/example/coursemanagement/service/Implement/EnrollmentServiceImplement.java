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
import java.util.Optional;
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
        List<EnrollmentEntity> enrollments = enrollmentRepository.findAll();
        return enrollments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EnrollmentDTO> getEnrollmentsByCourseId(Integer courseId) {
        List<EnrollmentEntity> enrollments = enrollmentRepository.findByCourse_CourseId(courseId);
        return enrollments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EnrollmentDTO addEnrollment(EnrollmentDTO enrollmentDTO) {
        if (enrollmentDTO.getUserId() == null || enrollmentDTO.getCourseId() == null) {
            throw new IllegalArgumentException("UserId và CourseId là bắt buộc.");
        }

        boolean exists = enrollmentRepository
                .findByUser_UserIdAndCourse_CourseId(enrollmentDTO.getUserId(), enrollmentDTO.getCourseId())
                .isPresent();

        if (exists) {
            throw new IllegalArgumentException("User đã đăng ký khóa học này rồi.");
        }

        EnrollmentEntity enrollmentEntity = convertToEntity(enrollmentDTO);

        if (enrollmentDTO.getStatus() == null) {
            enrollmentEntity.setStatus(EnrollmentEntity.EnrollmentStatus.in_progress);
        }
        if (enrollmentDTO.getPaymentStatus() == null) {
            enrollmentEntity.setPaymentStatus(EnrollmentEntity.PaymentStatus.pending);
        }

        EnrollmentEntity savedEntity = enrollmentRepository.save(enrollmentEntity);
        return convertToDTO(savedEntity);
    }

    @Override
    public EnrollmentDTO updateEnrollmentStatus(EnrollmentDTO enrollmentDTO) {
        EnrollmentEntity enrollmentEntity = enrollmentRepository.findById(enrollmentDTO.getEnrollmentId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid enrollment ID"));

        enrollmentEntity.setStatus(EnrollmentEntity.EnrollmentStatus.valueOf(enrollmentDTO.getStatus().name()));
//        enrollmentEntity.setPaymentStatus(EnrollmentEntity.PaymentStatus.valueOf(enrollmentDTO.getPaymentStatus().name()));
        EnrollmentEntity updatedEntity = enrollmentRepository.save(enrollmentEntity);
        return convertToDTO(updatedEntity);
    }
    @Override
    public EnrollmentDTO getEnrollmentById(Integer enrollmentId) {
        EnrollmentEntity enrollmentEntity = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new IllegalArgumentException("Enrollment ID not found"));
        return convertToDTO(enrollmentEntity);
    }

    private EnrollmentDTO convertToDTO(EnrollmentEntity enrollmentEntity) {
        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setEnrollmentId(enrollmentEntity.getEnrollmentId());
        dto.setUserId(enrollmentEntity.getUser().getUserId());
        dto.setUserName(enrollmentEntity.getUser().getName());
        dto.setCourseId(enrollmentEntity.getCourse().getCourseId());
        dto.setCourseName(enrollmentEntity.getCourse().getTitle());
        dto.setTitle(enrollmentEntity.getCourse().getTitle());
        dto.setStatus(EnrollmentDTO.EnrollmentStatus.valueOf(enrollmentEntity.getStatus().name()));
        dto.setEnrollmentDate(enrollmentEntity.getEnrollmentDate());
        dto.setPaymentStatus(EnrollmentDTO.PaymentStatus.valueOf(enrollmentEntity.getPaymentStatus().name()));
        return dto;
    }

    private EnrollmentEntity convertToEntity(EnrollmentDTO enrollmentDTO) {
        UserEntity user = userRepository.findById(enrollmentDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        CourseEntity course = courseRepository.findById(enrollmentDTO.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid course ID"));

        EnrollmentEntity entity = new EnrollmentEntity();
        entity.setEnrollmentId(enrollmentDTO.getEnrollmentId());
        entity.setUser(user);
        entity.setCourse(course);

        entity.setStatus(enrollmentDTO.getStatus() != null
                ? EnrollmentEntity.EnrollmentStatus.valueOf(enrollmentDTO.getStatus().name())
                : EnrollmentEntity.EnrollmentStatus.in_progress);

        entity.setPaymentStatus(enrollmentDTO.getPaymentStatus() != null
                ? EnrollmentEntity.PaymentStatus.valueOf(enrollmentDTO.getPaymentStatus().name())
                : EnrollmentEntity.PaymentStatus.pending);

        entity.setEnrollmentDate(enrollmentDTO.getEnrollmentDate());
        return entity;
    }
}
