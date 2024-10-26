package com.example.coursemanagement.data.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChoiceDTO {
    private Integer choiceId;
    private Integer questionId;
    private String choiceText;
    private Boolean isCorrect;
}
