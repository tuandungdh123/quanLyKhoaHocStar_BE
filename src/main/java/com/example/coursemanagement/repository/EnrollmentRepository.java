package com.example.coursemanagement.repository;

import com.example.coursemanagement.data.entity.EnrollmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Integer> {
}
