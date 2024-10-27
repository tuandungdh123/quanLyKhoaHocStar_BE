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
    public List<LessonDTO> getAllLesson(){
        List<LessonEntity> courses = lessonRepository.findAll();
        return courses.stream().map(this::convertToDTO).collect(Collectors.toList());
    }


    public LessonDTO convertToDTO(LessonEntity lessonEntity) {
        if (lessonEntity == null) {
            return null;
        }
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

    @Override
    public LessonDTO addLesson(LessonDTO lessonDTO) {
        LessonEntity lessonEntity = convertToEntity(lessonDTO);
        LessonEntity savedEntity = lessonRepository.save(lessonEntity);
        return convertToDTO(savedEntity);
    }

    @Override
    public LessonDTO updateLesson(Integer lessonId, LessonDTO lessonDTO) {
        LessonEntity existingLesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found"));
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
        lessonRepository.deleteById(lessonId);
    }

    @Override
    public LessonDTO getLessonById(Integer lessonId) {
        var lessonEntityOptional = lessonRepository.findByLessonId(lessonId);
        if (lessonEntityOptional.isEmpty()) {
            throw new AppException(ErrorCode.LESSON_NOT_FOUND, "Lesson not found");
        }
        return convertToDTO(lessonEntityOptional.get());
    }

    @Override
    public List<LessonDTO> getLessonsByModuleId(Integer moduleId) {
        List<LessonEntity> lessons = lessonRepository.findByModule_ModuleId(moduleId);
        return lessons.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public LessonEntity convertToEntity(LessonDTO lessonDTO) {
        LessonEntity lessonEntity = new LessonEntity();
        lessonEntity.setLessonId(lessonDTO.getLessonId());

        ModuleEntity module = moduleRepository.findById(lessonDTO.getModule())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

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
