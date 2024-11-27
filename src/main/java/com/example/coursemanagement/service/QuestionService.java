package com.example.coursemanagement.service;


import com.example.coursemanagement.data.DTO.QuestionDTO;

import java.util.List;

public interface QuestionService {
    List<QuestionDTO> getAllQuestion();
    QuestionDTO getQuestionById(Integer id);
    QuestionDTO createQuestion(QuestionDTO questionDTO);
    QuestionDTO updateQuestion(Integer id, QuestionDTO questionDTO);
    void deleteQuestion(Integer id);
    List<QuestionDTO> getQuestionByQuizId(Integer quizId);
}
