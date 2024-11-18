package com.example.coursemanagement.repository;

import com.example.coursemanagement.data.entity.EnrollmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Integer> {
    List<EnrollmentEntity> findByCourse_CourseId(Integer courseId);
    Optional<EnrollmentEntity> findByUser_UserIdAndCourse_CourseId(Integer userId, Integer courseId);
    List<EnrollmentEntity> findByUser_UserId(Integer userId);
    @Query("SELECT COUNT(e) FROM EnrollmentEntity e JOIN e.course c WHERE c.price = 0")
    long countFreeCourses();

    @Query("SELECT COUNT(e) FROM EnrollmentEntity e JOIN e.course c WHERE c.price > 0")
    long countProCourses();

    @Query("SELECT MONTH(e.enrollmentDate) AS month, SUM(c.price) AS totalRevenue " +
            "FROM EnrollmentEntity e JOIN e.course c " +
            "WHERE e.paymentStatus = 'completed' " +
            "GROUP BY MONTH(e.enrollmentDate) " +
            "ORDER BY MONTH(e.enrollmentDate)")
    List<Object[]> calculateMonthlyRevenue();


    EnrollmentEntity findByEnrollmentId(Integer enrollmentId);
}
