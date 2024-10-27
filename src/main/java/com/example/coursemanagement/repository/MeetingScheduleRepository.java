package com.example.coursemanagement.repository;

import com.example.coursemanagement.data.entity.MeetingScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingScheduleRepository extends JpaRepository<MeetingScheduleEntity,Integer> {
}
