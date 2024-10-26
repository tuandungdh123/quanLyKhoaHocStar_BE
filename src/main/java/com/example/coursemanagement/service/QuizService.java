package com.example.coursemanagement.service;

import com.example.coursemanagement.data.DTO.QuizDTO;

import java.util.List;

public interface QuizService {
    List<QuizDTO> getAllQuiz();
    QuizDTO getQuizById(Integer quizId);
    QuizDTO createQuiz(QuizDTO quizDTO);
    QuizDTO updateQuiz(Integer quizId, QuizDTO quizDTO);
    void deleteQuiz(Integer quizId);
}
