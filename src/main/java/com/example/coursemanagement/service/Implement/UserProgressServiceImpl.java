package com.example.coursemanagement.service.Implement;

import com.example.coursemanagement.data.DTO.UserProgressDTO;
import com.example.coursemanagement.data.entity.UserProgressEntity;
import com.example.coursemanagement.data.entity.UserEntity;
import com.example.coursemanagement.data.entity.CourseEntity;
import com.example.coursemanagement.exception.AppException;
import com.example.coursemanagement.exception.ErrorCode;
import com.example.coursemanagement.repository.UserProgressRepository;
import com.example.coursemanagement.repository.UserRepository;
import com.example.coursemanagement.repository.CourseRepository;
import com.example.coursemanagement.service.UserProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class UserProgressServiceImpl implements UserProgressService {
    private final UserProgressRepository userProgressRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Override
    public UserProgressDTO getUserProgress(Integer userId, Integer courseId) {
        return userProgressRepository.findByUser_UserIdAndCourse_CourseId(userId, courseId)
                .map(this::convertToDto)
                .orElseThrow(() -> new AppException(ErrorCode.PROGRESS_NOT_FOUND, "Không tìm thấy tiến trình của người dùng cho khóa học.")); // Thông báo lỗi bằng tiếng Việt
    }

    @Override
    public void updateUserProgress(UserProgressDTO userProgressDTO) {
        UserProgressEntity entity = userProgressRepository.findById(userProgressDTO.getProgressId())
                .orElseThrow(() -> new AppException(ErrorCode.PROGRESS_NOT_FOUND, "Không tìm thấy tiến trình với ID: " + userProgressDTO.getProgressId()));

        // Cập nhật thông tin từ DTO
        entity.setCompletedLessons(userProgressDTO.getCompletedLessons());
        entity.setLastAccessed(userProgressDTO.getLastAccessed() != null ? userProgressDTO.getLastAccessed() : new Timestamp(System.currentTimeMillis()));
        entity.setProgressPercentage(userProgressDTO.getProgressPercentage());

        // Cập nhật User
        UserEntity user = userRepository.findById(userProgressDTO.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, "Không tìm thấy người dùng với ID: " + userProgressDTO.getUserId()));
        entity.setUser(user);

        // Cập nhật Course
        CourseEntity course = courseRepository.findById(userProgressDTO.getCourseId())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND, "Không tìm thấy khóa học với ID: " + userProgressDTO.getCourseId()));
        entity.setCourse(course);

        userProgressRepository.save(entity);
    }

    private UserProgressDTO convertToDto(UserProgressEntity entity) {
        return UserProgressDTO.builder()
                .progressId(entity.getProgressId())
                .userId(entity.getUser().getUserId())
                .courseId(entity.getCourse().getCourseId())
                .completedLessons(entity.getCompletedLessons())
                .lastAccessed(entity.getLastAccessed())
                .progressPercentage(entity.getProgressPercentage())
                .build();
    }
}
