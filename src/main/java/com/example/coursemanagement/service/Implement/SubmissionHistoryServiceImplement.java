package com.example.coursemanagement.service.Implement;

import com.example.coursemanagement.data.DTO.SubmissionHistoryDTO;
import com.example.coursemanagement.data.entity.*;
import com.example.coursemanagement.exception.AppException;
import com.example.coursemanagement.exception.ErrorCode;
import com.example.coursemanagement.repository.*;
import com.example.coursemanagement.service.SubmissionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubmissionHistoryServiceImplement implements SubmissionHistoryService {
    private final SubmissionHistoryRepository submissionHistoryRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;
    private final QuizRepository quizRepository;

    @Override
    public List<SubmissionHistoryDTO> getAllSubmissionHistories() {
        List<SubmissionHistoryEntity> entities = submissionHistoryRepository.findAll();
        return entities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SubmissionHistoryDTO saveSubmissionHistory(SubmissionHistoryDTO submissionHistoryDTO) {
        // Kiểm tra dữ liệu: Các trường bắt buộc không được null
        if (submissionHistoryDTO.getUserId() == null ||
                submissionHistoryDTO.getCourseId() == null ||
                submissionHistoryDTO.getModuleId() == null ||
                submissionHistoryDTO.getQuizId() == null) {
            throw new AppException(ErrorCode.INVALID_CREDENTIALS,
                    "UserId, CourseId, ModuleId và QuizId là bắt buộc.");
        }

        // Kiểm tra xem lịch sử nộp bài đã tồn tại hay chưa (nếu cần)
        Optional<SubmissionHistoryEntity> existingHistory = submissionHistoryRepository
                .findByUser_UserIdAndCourse_CourseIdAndModule_ModuleIdAndQuiz_QuizId(
                        submissionHistoryDTO.getUserId(),
                        submissionHistoryDTO.getCourseId(),
                        submissionHistoryDTO.getModuleId(),
                        submissionHistoryDTO.getQuizId()
                );

        if (existingHistory.isPresent()) {
            throw new AppException(ErrorCode.DUPLICATE_SUBMISSION, "Lịch sử nộp bài đã tồn tại.");
        }

        SubmissionHistoryEntity entity = convertToEntity(submissionHistoryDTO);
        SubmissionHistoryEntity savedEntity = submissionHistoryRepository.save(entity);

        return convertToDTO(savedEntity);
    }


    @Override
    public SubmissionHistoryDTO getSubmissionHistoryById(Integer submissionHistoryId) {
        SubmissionHistoryEntity submissionHistoryEntity = submissionHistoryRepository.findById(submissionHistoryId)
                .orElseThrow(() -> new AppException(ErrorCode.ENROLLMENT_NOT_FOUND, "Không tìm thấy bản ghi với ID: " + submissionHistoryId));
        return convertToDTO(submissionHistoryEntity);
    }

    @Override
    public List<SubmissionHistoryDTO> getSubmissionHistoriesByModuleId(Integer moduleId) {
        return submissionHistoryRepository.findByModule_ModuleId(moduleId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SubmissionHistoryDTO> getSubmissionHistoriesByUserIdAndCourseId(Integer userId, Integer courseId) {
        return submissionHistoryRepository.findByUser_UserIdAndCourse_CourseId(userId, courseId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public SubmissionHistoryDTO convertToDTO(SubmissionHistoryEntity submissionHistoryEntity) {
        SubmissionHistoryDTO dto = new SubmissionHistoryDTO();
        dto.setHistoryId(submissionHistoryEntity.getHistoryId());
        dto.setUserId(submissionHistoryEntity.getUser() != null ? submissionHistoryEntity.getUser().getUserId() : null);
        dto.setCourseId(submissionHistoryEntity.getCourse() != null ? submissionHistoryEntity.getCourse().getCourseId() : null);
        dto.setModuleId(submissionHistoryEntity.getModule() != null ? submissionHistoryEntity.getModule().getModuleId() : null);
        dto.setQuizId(submissionHistoryEntity.getQuiz() != null ? submissionHistoryEntity.getQuiz().getQuizId() : null);
        dto.setScore(submissionHistoryEntity.getScore());
        dto.setAssignmentStatus(submissionHistoryEntity.getAssignmentStatus());
        dto.setCreatedAt(submissionHistoryEntity.getCreatedAt());
        return dto;
    }

    public SubmissionHistoryEntity convertToEntity(SubmissionHistoryDTO submissionHistoryDTO) {
        SubmissionHistoryEntity entity = new SubmissionHistoryEntity();
        entity.setHistoryId(submissionHistoryDTO.getHistoryId());

        Optional<UserEntity> user = userRepository.findById(submissionHistoryDTO.getUserId());
        Optional<CourseEntity> course = courseRepository.findById(submissionHistoryDTO.getCourseId());
        Optional<ModuleEntity> module = moduleRepository.findById(submissionHistoryDTO.getModuleId());
        Optional<QuizEntity> quiz = quizRepository.findById(submissionHistoryDTO.getQuizId());

        user.ifPresent(entity::setUser);
        course.ifPresent(entity::setCourse);
        module.ifPresent(entity::setModule);
        quiz.ifPresent(entity::setQuiz);

        entity.setScore(submissionHistoryDTO.getScore());
        entity.setAssignmentStatus(submissionHistoryDTO.getAssignmentStatus());
        entity.setCreatedAt(submissionHistoryDTO.getCreatedAt() != null ? submissionHistoryDTO.getCreatedAt() : LocalDateTime.now());

        return entity;
    }
}
