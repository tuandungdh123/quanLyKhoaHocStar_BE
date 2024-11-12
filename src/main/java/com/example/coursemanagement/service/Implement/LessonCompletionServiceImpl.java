package com.example.coursemanagement.service.Implement;

import com.example.coursemanagement.data.DTO.LessonCompletionDTO;
import com.example.coursemanagement.data.entity.LessonCompletionEntity;
import com.example.coursemanagement.data.entity.UserEntity;
import com.example.coursemanagement.data.entity.LessonEntity;
import com.example.coursemanagement.exception.AppException;
import com.example.coursemanagement.exception.ErrorCode;
import com.example.coursemanagement.repository.LessonCompletionRepository;
import com.example.coursemanagement.repository.UserRepository;
import com.example.coursemanagement.repository.LessonRepository;
import com.example.coursemanagement.service.LessonCompletionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonCompletionServiceImpl implements LessonCompletionService {
    private final LessonCompletionRepository lessonCompletionRepository;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;

    @Override
    public void completeLesson(LessonCompletionDTO lessonCompletionDTO) {
        UserEntity user = userRepository.findById(lessonCompletionDTO.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, "Người dùng không tồn tại."));

        LessonEntity lesson = lessonRepository.findById(lessonCompletionDTO.getLessonId())
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND, "Bài học không tồn tại."));

        LessonCompletionEntity entity = new LessonCompletionEntity();
        entity.setUser(user);
        entity.setLesson(lesson);
        entity.setCompletedAt(lessonCompletionDTO.getCompletedAt() != null ? lessonCompletionDTO.getCompletedAt() : new Timestamp(System.currentTimeMillis()));

        lessonCompletionRepository.save(entity);
    }

    @Override
    public List<LessonCompletionDTO> getCompletedLessons(Integer userId, Integer courseId) {
        List<LessonCompletionEntity> completedLessons = lessonCompletionRepository.findByUser_UserIdAndLesson_LessonId(userId, courseId);
        return completedLessons.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private LessonCompletionDTO convertToDto(LessonCompletionEntity entity) {
        return LessonCompletionDTO.builder()
                .completionId(entity.getCompletionId())
                .userId(entity.getUser().getUserId())
                .lessonId(entity.getLesson().getLessonId())
                .completedAt(entity.getCompletedAt())
                .build();
    }
}
