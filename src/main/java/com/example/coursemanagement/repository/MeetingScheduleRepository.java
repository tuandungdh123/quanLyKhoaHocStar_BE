package com.example.coursemanagement.repository;

import com.example.coursemanagement.data.entity.MeetingScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingScheduleRepository extends JpaRepository<MeetingScheduleEntity,Integer> {
    List<MeetingScheduleEntity> findByCourse_CourseId(Integer courseId);
}
