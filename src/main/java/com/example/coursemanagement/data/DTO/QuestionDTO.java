package com.example.coursemanagement.data.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    private Integer questionId;
    private Integer quizId;
    private String questionText;
    private Boolean isRequired;
}
