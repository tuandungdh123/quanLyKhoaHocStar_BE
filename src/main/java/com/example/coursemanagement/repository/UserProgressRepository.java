package com.example.coursemanagement.repository;

import com.example.coursemanagement.data.entity.UserProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProgressRepository extends JpaRepository<UserProgressEntity, Integer> {
    List<UserProgressEntity> findByUser_UserId(Integer userId);
    List<UserProgressEntity> findByUser_UserIdAndCourse_CourseId(Integer userId, Integer courseId);
    UserProgressEntity findByLesson_LessonId(Integer lessonId);
    Optional<UserProgressEntity> findByUser_UserIdAndCourse_CourseIdAndLesson_LessonId(Integer userId, Integer courseId, Integer lessonId);
}
