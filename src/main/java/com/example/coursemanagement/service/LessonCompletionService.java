package com.example.coursemanagement.service;

import com.example.coursemanagement.data.DTO.LessonCompletionDTO;

import java.util.List;

public interface LessonCompletionService {
    void completeLesson(LessonCompletionDTO lessonCompletionDTO);
    List<LessonCompletionDTO> getCompletedLessons(Integer userId, Integer courseId);
}
