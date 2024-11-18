package com.example.coursemanagement.repository;

import com.example.coursemanagement.data.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, Integer> {
    Optional<LessonEntity> findByLessonId(Integer lessonId);
    List<LessonEntity> findByModule_ModuleId(Integer moduleId);
}
