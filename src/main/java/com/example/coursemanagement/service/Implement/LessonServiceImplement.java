package com.example.coursemanagement.service.Implement;

import com.example.coursemanagement.data.DTO.LessonDTO;
import com.example.coursemanagement.data.entity.LessonEntity;
import com.example.coursemanagement.data.entity.ModuleEntity;
import com.example.coursemanagement.exception.AppException;
import com.example.coursemanagement.exception.ErrorCode;
import com.example.coursemanagement.repository.LessonRepository;
import com.example.coursemanagement.repository.ModuleRepository;
import com.example.coursemanagement.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LessonServiceImplement implements LessonService {
    private final LessonRepository lessonRepository;
    private final ModuleRepository moduleRepository;

    @Override
    public List<LessonDTO> getAllLesson() {
        return lessonRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LessonDTO addLesson(LessonDTO lessonDTO) {
        LessonEntity lessonEntity = convertToEntity(lessonDTO);
        LessonEntity savedEntity = lessonRepository.save(lessonEntity);
        return convertToDTO(savedEntity);
    }

    @Override
    public LessonDTO updateLesson(Integer lessonId, LessonDTO lessonDTO) {
        LessonEntity existingLesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND, "Bài học không tìm thấy."));

        existingLesson.setTitle(lessonDTO.getTitle());
        existingLesson.setContent(lessonDTO.getContent());
        existingLesson.setVideoUrl(lessonDTO.getVideoUrl());
        existingLesson.setDuration(lessonDTO.getDuration());
        existingLesson.setStatus(LessonEntity.LessonStatus.valueOf(lessonDTO.getStatus().name()));
        existingLesson.setOrderNumber(lessonDTO.getOrderNumber());

        LessonEntity updatedEntity = lessonRepository.save(existingLesson);
        return convertToDTO(updatedEntity);
    }

    @Override
    public void deleteLesson(Integer lessonId) {
        if (!lessonRepository.existsById(lessonId)) {
            throw new AppException(ErrorCode.LESSON_NOT_FOUND, "Bài học không tìm thấy.");
        }
        lessonRepository.deleteById(lessonId);
    }

    @Override
    public LessonDTO getLessonById(Integer lessonId) {
        return lessonRepository.findByLessonId(lessonId)
                .map(this::convertToDTO)
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND, "Bài học không tìm thấy."));
    }

    @Override
    public List<LessonDTO> getLessonsByModuleId(Integer moduleId) {
        return lessonRepository.findByModule_ModuleId(moduleId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private LessonDTO convertToDTO(LessonEntity lessonEntity) {
        return LessonDTO.builder()
                .lessonId(lessonEntity.getLessonId())
                .module(lessonEntity.getModule().getModuleId())
                .title(lessonEntity.getTitle())
                .content(lessonEntity.getContent())
                .videoUrl(lessonEntity.getVideoUrl())
                .duration(lessonEntity.getDuration())
                .status(LessonDTO.LessonStatus.valueOf(lessonEntity.getStatus().name()))
                .orderNumber(lessonEntity.getOrderNumber())
                .createdAt(lessonEntity.getCreatedAt())
                .build();
    }

    private LessonEntity convertToEntity(LessonDTO lessonDTO) {
        ModuleEntity module = moduleRepository.findById(lessonDTO.getModule())
                .orElseThrow(() -> new AppException(ErrorCode.MODULE_NOT_FOUND, "Module không tồn tại."));

        LessonEntity lessonEntity = new LessonEntity();
        lessonEntity.setLessonId(lessonDTO.getLessonId());
        lessonEntity.setModule(module);
        lessonEntity.setTitle(lessonDTO.getTitle());
        lessonEntity.setContent(lessonDTO.getContent());
        lessonEntity.setVideoUrl(lessonDTO.getVideoUrl());
        lessonEntity.setDuration(lessonDTO.getDuration());
        lessonEntity.setStatus(LessonEntity.LessonStatus.valueOf(lessonDTO.getStatus().name()));
        lessonEntity.setOrderNumber(lessonDTO.getOrderNumber());
        lessonEntity.setCreatedAt(lessonDTO.getCreatedAt());

        return lessonEntity;
    }
}
