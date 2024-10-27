package com.example.coursemanagement.repository;

import com.example.coursemanagement.data.entity.SubmissionHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionHistoryRepository extends JpaRepository<SubmissionHistoryEntity,Integer> {
}
