package com.example.coursemanagement.repository;

import com.example.coursemanagement.data.entity.CourseEntity;
import com.example.coursemanagement.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Integer> {
    Optional<CourseEntity> findByCourseId(Integer courseId);
}
