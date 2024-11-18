package com.example.coursemanagement.service.Implement;

import com.example.coursemanagement.data.DTO.EnrollmentDTO;
import com.example.coursemanagement.data.Enums.PaymentStatus;
import com.example.coursemanagement.data.entity.CourseEntity;
import com.example.coursemanagement.data.entity.EnrollmentEntity;
import com.example.coursemanagement.data.entity.UserEntity;
import com.example.coursemanagement.exception.AppException;
import com.example.coursemanagement.exception.ErrorCode;
import com.example.coursemanagement.repository.CourseRepository;
import com.example.coursemanagement.repository.EnrollmentRepository;
import com.example.coursemanagement.repository.UserRepository;
import com.example.coursemanagement.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImplement implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

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
        return enrollments.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public EnrollmentDTO addEnrollment(EnrollmentDTO enrollmentDTO) {
        if (enrollmentDTO.getUserId() == null || enrollmentDTO.getCourseId() == null) {
            throw new AppException(ErrorCode.INVALID_CREDENTIALS, "UserId và CourseId là bắt buộc.");
        }

        boolean exists = enrollmentRepository
                .findByUser_UserIdAndCourse_CourseId(enrollmentDTO.getUserId(), enrollmentDTO.getCourseId())
                .isPresent();

        if (exists) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS, "Người dùng đã đăng ký khóa học này rồi.");
        }

        EnrollmentEntity enrollmentEntity = convertToEntity(enrollmentDTO);

        // Kiểm tra số tiền của khóa học
        CourseEntity course = courseRepository.findById(enrollmentDTO.getCourseId())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND, "ID khóa học không hợp lệ."));

        // Nếu khóa học có giá trị > 0 thì trạng thái thanh toán là pending, ngược lại là completed
        PaymentStatus paymentStatus = course.getPrice() > 0 ? PaymentStatus.pending : PaymentStatus.completed;
        enrollmentEntity.setPaymentStatus(enrollmentDTO.getPaymentStatus() != null
                ? EnrollmentEntity.PaymentStatus.valueOf(enrollmentDTO.getPaymentStatus().name())
                : EnrollmentEntity.PaymentStatus.valueOf(paymentStatus.name())); // Sử dụng trạng thái tính toán từ giá trị khóa học

        enrollmentEntity.setStatus(enrollmentDTO.getStatus() != null
                ? EnrollmentEntity.EnrollmentStatus.valueOf(enrollmentDTO.getStatus().name())
                : EnrollmentEntity.EnrollmentStatus.in_progress);

        EnrollmentEntity savedEntity = enrollmentRepository.save(enrollmentEntity);
        return convertToDTO(savedEntity);
    }


    @Override
    public List<EnrollmentDTO> getAllEnrollmentsByUserId(Integer userId) {
        List<EnrollmentEntity> enrollments = enrollmentRepository.findByUser_UserId(userId);
        if (enrollments.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND, "Người dùng chưa đăng ký bất kỳ khóa học nào.");
        }
        return enrollments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EnrollmentDTO updateEnrollmentStatus(EnrollmentDTO enrollmentDTO) {
        EnrollmentEntity enrollmentEntity = enrollmentRepository.findById(enrollmentDTO.getEnrollmentId())
                .orElseThrow(() -> new AppException(ErrorCode.ENROLLMENT_NOT_FOUND, "Enrollment ID không hợp lệ."));

        enrollmentEntity.setStatus(EnrollmentEntity.EnrollmentStatus.valueOf(enrollmentDTO.getStatus().name()));
        EnrollmentEntity updatedEntity = enrollmentRepository.save(enrollmentEntity);
        return convertToDTO(updatedEntity);
    }

    @Override
    public EnrollmentDTO getEnrollmentById(Integer enrollmentId) {
        EnrollmentEntity enrollmentEntity = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new AppException(ErrorCode.ENROLLMENT_NOT_FOUND, "Enrollment ID không tìm thấy."));
        return convertToDTO(enrollmentEntity);
    }

    @Override
    public EnrollmentDTO checkEnrollment(Integer userId, Integer courseId) {
        // Lấy thông tin đăng ký của người dùng và khóa học từ database
        Optional<EnrollmentEntity> enrollmentEntityOpt = enrollmentRepository.findByUser_UserIdAndCourse_CourseId(userId, courseId);

        // Nếu có thông tin đăng ký
        if (enrollmentEntityOpt.isPresent()) {
            EnrollmentEntity enrollmentEntity = enrollmentEntityOpt.get();

            // Nếu trạng thái thanh toán là pending, trả về thông tin đăng ký
            if (enrollmentEntity.getPaymentStatus() == EnrollmentEntity.PaymentStatus.pending || enrollmentEntity.getPaymentStatus() == EnrollmentEntity.PaymentStatus.failed) {
                return convertToDTO(enrollmentEntity);
            }
        }

        // Nếu không tìm thấy đăng ký hoặc thanh toán không phải pending, trả về null
        return null;
    }

    private EnrollmentDTO convertToDTO(EnrollmentEntity enrollmentEntity) {
        return EnrollmentDTO.builder()
                .enrollmentId(enrollmentEntity.getEnrollmentId())
                .userId(enrollmentEntity.getUser().getUserId())
                .userName(enrollmentEntity.getUser().getName())
                .courseId(enrollmentEntity.getCourse().getCourseId())
                .courseName(enrollmentEntity.getCourse().getTitle())
                .imgUrl(enrollmentEntity.getCourse().getImgUrl())
                .description(enrollmentEntity.getCourse().getDescription())
                .title(enrollmentEntity.getCourse().getTitle())
                .status(EnrollmentDTO.EnrollmentStatus.valueOf(enrollmentEntity.getStatus().name()))
                .enrollmentDate(enrollmentEntity.getEnrollmentDate())
                .paymentStatus(EnrollmentDTO.PaymentStatus.valueOf(enrollmentEntity.getPaymentStatus().name()))
                .build();
    }

    private EnrollmentEntity convertToEntity(EnrollmentDTO enrollmentDTO) {
        UserEntity user = userRepository.findById(enrollmentDTO.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, "ID người dùng không hợp lệ."));

        CourseEntity course = courseRepository.findById(enrollmentDTO.getCourseId())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND, "ID khóa học không hợp lệ."));

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
