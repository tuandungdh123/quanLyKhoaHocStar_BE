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
public class UserProgressDTO {
    private Integer progressId;
    private Integer userId;
    private Integer courseId;
    private Integer lessonId;
    private LocalDateTime createdAt = LocalDateTime.now();
    private String status;
}
