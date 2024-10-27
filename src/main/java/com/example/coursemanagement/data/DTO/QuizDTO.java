package com.example.coursemanagement.data.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizDTO {
    private Integer quizId;
    private Integer moduleId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
}
