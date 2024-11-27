package com.example.coursemanagement.repository;

import com.example.coursemanagement.data.entity.ChoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChoiceRepository extends JpaRepository<ChoiceEntity, Integer> {
    List<ChoiceEntity> findByQuestion_QuestionId(Integer questionId);
}
