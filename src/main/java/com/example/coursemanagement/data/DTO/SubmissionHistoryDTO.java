package com.example.coursemanagement.data.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionHistoryDTO {
    private Integer historyId;
    private Integer userId;
    private Integer courseId;
    private Integer moduleId;
    private Integer quizId;
    private Integer questionId;
    private Double score;
    private Boolean assignmentStatus;
    private LocalDateTime createdAt;
}

