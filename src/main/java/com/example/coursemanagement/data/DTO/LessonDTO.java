package com.example.coursemanagement.data.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonDTO {
    private Integer lessonId;
    private Integer module;
    private String title;
    private String content;
    private String videoUrl;
    private Integer duration;
    private LessonStatus status;
    private Integer orderNumber;
    private LocalDateTime createdAt;

    public enum LessonStatus {
        completed,
        not_completed,
    }
}
