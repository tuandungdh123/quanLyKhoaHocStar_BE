package com.example.coursemanagement.repository;

import com.example.coursemanagement.data.entity.EnrollmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Integer> {
    List<EnrollmentEntity> findByCourse_CourseId(Integer courseId);
    Optional<EnrollmentEntity> findByUser_UserIdAndCourse_CourseId(Integer userId, Integer courseId);
    List<EnrollmentEntity> findByUser_UserId(Integer userId);
}
