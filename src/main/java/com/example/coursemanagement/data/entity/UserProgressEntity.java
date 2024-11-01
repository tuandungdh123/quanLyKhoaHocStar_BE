package com.example.coursemanagement.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "User_Progress")
public class UserProgressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer progressId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity course;

    @Column(columnDefinition = "json")
    private String completedLessons;

    @Column(name = "last_accessed", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp lastAccessed;

    @Column(name = "progress_percentage", nullable = false)
    private Double progressPercentage = 0.0;
}

