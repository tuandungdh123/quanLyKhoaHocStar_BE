package com.example.coursemanagement.service.Implement;

import com.example.coursemanagement.data.DTO.UserProgressDTO;
import com.example.coursemanagement.data.entity.CourseEntity;
import com.example.coursemanagement.data.entity.LessonEntity;
import com.example.coursemanagement.data.entity.UserEntity;
import com.example.coursemanagement.data.entity.UserProgressEntity;
import com.example.coursemanagement.exception.AppException;
import com.example.coursemanagement.exception.ErrorCode;
import com.example.coursemanagement.repository.CourseRepository;
import com.example.coursemanagement.repository.LessonRepository;
import com.example.coursemanagement.repository.UserProgressRepository;
import com.example.coursemanagement.repository.UserRepository;
import com.example.coursemanagement.service.UserProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserProgressServiceImpl implements UserProgressService {
    private final UserProgressRepository userProgressRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;

    @Override
    public List<UserProgressDTO> getAllUserProgress() {
        return userProgressRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserProgressDTO addUserProgress(UserProgressDTO userProgressDTO) {
        if (userProgressDTO.getUserId() == null || userProgressDTO.getCourseId() == null || userProgressDTO.getLessonId() == null) {
            throw new AppException(ErrorCode.INVALID_CREDENTIALS, "UserId, CourseId và LessonId là bắt buộc.");
        }

        // Kiểm tra trùng lặp
        Optional<UserProgressEntity> existingProgress = userProgressRepository.findByUser_UserIdAndCourse_CourseIdAndLesson_LessonId(
                userProgressDTO.getUserId(),
                userProgressDTO.getCourseId(),
                userProgressDTO.getLessonId()
        );

        if (existingProgress.isPresent()) {
            throw new AppException(ErrorCode.DUPLICATE_PROGRESS, "Tiến trình học tập đã tồn tại.");
        }

        UserProgressEntity userProgressEntity = convertToEntity(userProgressDTO);
        UserProgressEntity savedEntity = userProgressRepository.save(userProgressEntity);

        return convertToDTO(savedEntity);
    }

    @Override
    public UserProgressDTO updateUserProgress(Integer progressId, UserProgressDTO userProgressDTO) {
        UserProgressEntity existingProgress = userProgressRepository.findById(progressId)
                .orElseThrow(() -> new AppException(ErrorCode.PROGRESS_NOT_FOUND, "Không tìm thấy tiến trình học nào."));

        LessonEntity lesson = lessonRepository.findById(userProgressDTO.getLessonId())
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND, "Không tìm thấy bài học nào."));
        existingProgress.setLesson(lesson);

        CourseEntity course = courseRepository.findById(userProgressDTO.getCourseId())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND, "Không tìm thấy khóa học nào."));
        existingProgress.setCourse(course);

        existingProgress.setStatus(UserProgressEntity.ProgressStatus.valueOf(userProgressDTO.getStatus()));

        UserProgressEntity updatedEntity = userProgressRepository.save(existingProgress);
        return convertToDTO(updatedEntity);
    }

    @Override
    public void deleteUserProgress(Integer progressId) {
        if (!userProgressRepository.existsById(progressId)) {
            throw new AppException(ErrorCode.PROGRESS_NOT_FOUND, "Không tìm thấy tiến trình học nào.");
        }
        userProgressRepository.deleteById(progressId);
    }

    @Override
    public UserProgressDTO getUserProgressById(Integer progressId) {
        return userProgressRepository.findById(progressId)
                .map(this::convertToDTO)
                .orElseThrow(() -> new AppException(ErrorCode.PROGRESS_NOT_FOUND, "Không tìm thấy tiến trình học nào."));
    }

    @Override
    public List<UserProgressDTO> getUserProgressByUserId(Integer userId) {
        return userProgressRepository.findByUser_UserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserProgressDTO> getUserProgressByUserIdAndCourseId(Integer userId, Integer courseId) {
        return userProgressRepository.findByUser_UserIdAndCourse_CourseId(userId, courseId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private UserProgressDTO convertToDTO(UserProgressEntity userProgressEntity) {
        return UserProgressDTO.builder()
                .progressId(userProgressEntity.getProgressId())
                .userId(userProgressEntity.getUser().getUserId())
                .courseId(userProgressEntity.getCourse().getCourseId())
                .lessonId(userProgressEntity.getLesson().getLessonId())
                .createdAt(userProgressEntity.getCreatedAt())
                .status(String.valueOf(userProgressEntity.getStatus()))
                .build();
    }

    private UserProgressEntity convertToEntity(UserProgressDTO userProgressDTO) {
        UserProgressEntity userProgressEntity = new UserProgressEntity();

        UserEntity user = userRepository.findById(userProgressDTO.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, "Không tìm thấy người dùng nào."));
        CourseEntity course = courseRepository.findById(userProgressDTO.getCourseId())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND, "Không tìm thấy khóa học nào."));
        LessonEntity lesson = lessonRepository.findById(userProgressDTO.getLessonId())
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND, "Không tìm thấy bài học nào."));

        userProgressEntity.setUser(user);
        userProgressEntity.setCourse(course);
        userProgressEntity.setLesson(lesson);
        userProgressEntity.setCreatedAt(userProgressDTO.getCreatedAt());
        userProgressEntity.setStatus(UserProgressEntity.ProgressStatus.valueOf(userProgressDTO.getStatus()));

        return userProgressEntity;
    }
}
