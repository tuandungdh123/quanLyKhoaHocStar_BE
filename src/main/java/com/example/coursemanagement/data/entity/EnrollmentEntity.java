package com.example.coursemanagement.data.entity;



import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "enrollments")
public class EnrollmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer enrollmentId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity course;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollmentStatus status;

    @Column(name = "enrollment_date", updatable = false)
    private LocalDateTime enrollmentDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "certificate_url")
    private String certificateUrl;

    @Column(name = "issue_date")
    private LocalDateTime issueDate;

    public enum EnrollmentStatus {
        completed,
        in_progress
    }

    public enum PaymentStatus {
        pending,
        completed,
        failed
    }

    public void completeCourse(String certificateUrl) {
        if (this.status == EnrollmentStatus.completed) {
            throw new IllegalStateException("Course already completed.");
        }
        this.status = EnrollmentStatus.completed;
        this.certificateUrl = certificateUrl;
        this.issueDate = LocalDateTime.now();
    }

}
