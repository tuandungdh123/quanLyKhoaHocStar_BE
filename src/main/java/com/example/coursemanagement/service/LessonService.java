package com.example.coursemanagement.service;

import com.example.coursemanagement.data.DTO.LessonDTO;

import java.util.List;

public interface LessonService {
    List<LessonDTO> getAllLesson();
    LessonDTO addLesson(LessonDTO lessonDTO);
    LessonDTO updateLesson(Integer lessonId, LessonDTO lessonDTO);
    void deleteLesson(Integer lessonId);
    LessonDTO getLessonById(Integer lessonId);
    List<LessonDTO> getLessonsByModuleId(Integer lessonId);
}
