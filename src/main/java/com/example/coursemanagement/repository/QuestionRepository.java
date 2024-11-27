package com.example.coursemanagement.repository;


import com.example.coursemanagement.data.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Integer> {
    List<QuestionEntity> findByQuiz_QuizId(Integer moduleId);
}
