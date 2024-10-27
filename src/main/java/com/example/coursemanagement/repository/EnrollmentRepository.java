package com.example.coursemanagement.repository;

import com.example.coursemanagement.data.entity.EnrollmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Integer> {
    List<EnrollmentEntity> findByCourse_CourseId(Integer courseId);
}
