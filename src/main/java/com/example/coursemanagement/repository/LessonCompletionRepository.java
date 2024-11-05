package com.example.coursemanagement.repository;

import com.example.coursemanagement.data.entity.LessonCompletionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonCompletionRepository extends JpaRepository<LessonCompletionEntity, Integer> {
    List<LessonCompletionEntity> findByUser_UserIdAndLesson_LessonId(Integer userId, Integer lessonId);
}
