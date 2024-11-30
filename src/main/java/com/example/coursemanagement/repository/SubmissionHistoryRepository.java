package com.example.coursemanagement.repository;

import com.example.coursemanagement.data.entity.SubmissionHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubmissionHistoryRepository extends JpaRepository<SubmissionHistoryEntity,Integer> {
    List<SubmissionHistoryEntity> findByModule_ModuleId(Integer moduleId);
    List<SubmissionHistoryEntity> findByUser_UserIdAndCourse_CourseId(Integer userId, Integer courseId);
    Optional<SubmissionHistoryEntity> findByUser_UserIdAndCourse_CourseIdAndModule_ModuleIdAndQuiz_QuizId(Integer userId, Integer courseId, Integer moduleId, Integer quizId);
}
