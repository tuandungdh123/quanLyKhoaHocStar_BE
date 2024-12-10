package com.example.coursemanagement.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lessons")
public class LessonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lessonId;

    @ManyToOne
    @JoinColumn(name = "module_id", nullable = false)
    private ModuleEntity module;

    @Column(nullable = false, length = 255)
    private String title;

    private String content;
    private String videoUrl;

    private Integer duration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LessonStatus status = LessonStatus.not_completed;

    @Column(name = "order_number", nullable = false)
    private Integer orderNumber;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum LessonStatus {
        completed,
        not_completed,
    }
}
