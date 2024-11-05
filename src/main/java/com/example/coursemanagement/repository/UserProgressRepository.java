package com.example.coursemanagement.repository;

import com.example.coursemanagement.data.entity.UserProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProgressRepository extends JpaRepository<UserProgressEntity, Integer> {
    Optional<UserProgressEntity> findByUser_UserIdAndCourse_CourseId(Integer userId, Integer courseId);
}
