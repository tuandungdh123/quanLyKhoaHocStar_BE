package com.example.coursemanagement.repository;

import com.example.coursemanagement.data.entity.ModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModuleRepository extends JpaRepository<ModuleEntity, Integer> {
    Optional<ModuleEntity> findByModuleId(Integer moduleId);
    List<ModuleEntity> findByCourse_CourseId(Integer courseId);
}
