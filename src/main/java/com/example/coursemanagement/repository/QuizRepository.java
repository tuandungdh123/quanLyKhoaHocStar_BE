package com.example.coursemanagement.repository;

import com.example.coursemanagement.data.entity.ModuleEntity;
import com.example.coursemanagement.data.entity.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<QuizEntity, Integer> {
    List<QuizEntity> findByModule_ModuleId(Integer moduleId);
}
