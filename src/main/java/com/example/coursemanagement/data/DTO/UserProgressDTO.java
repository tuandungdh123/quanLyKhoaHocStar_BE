package com.example.coursemanagement.data.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProgressDTO {
    private Integer progressId;
    private Integer userId;
    private Integer courseId;
    private String completedLessons; // JSON lưu danh sách lesson_id đã hoàn thành
    private Timestamp lastAccessed = Timestamp.valueOf(LocalDateTime.now());;
    private Double progressPercentage = 0.0;
}
