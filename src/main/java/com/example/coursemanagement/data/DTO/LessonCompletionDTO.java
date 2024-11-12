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
public class LessonCompletionDTO {
    private Integer completionId;
    private Integer userId;
    private Integer lessonId;
    private Timestamp completedAt = Timestamp.valueOf(LocalDateTime.now());;
}
