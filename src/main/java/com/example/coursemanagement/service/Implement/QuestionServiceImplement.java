package com.example.coursemanagement.service.Implement;


import com.example.coursemanagement.data.DTO.QuestionDTO;
import com.example.coursemanagement.data.entity.QuestionEntity;
import com.example.coursemanagement.data.entity.QuizEntity;
import com.example.coursemanagement.repository.QuestionRepository;
import com.example.coursemanagement.repository.QuizRepository;
import com.example.coursemanagement.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImplement  implements QuestionService {
    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;

    @Override
    public List<QuestionDTO> getAllQuestion(){
        List<QuestionEntity> courses = questionRepository.findAll();
        return courses.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public QuestionDTO getQuestionById(Integer id) {
        QuestionEntity questionEntity = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with ID: " + id));
        return convertToDTO(questionEntity);
    }

    @Override
    public QuestionDTO createQuestion(QuestionDTO questionDTO) {
        QuizEntity quizEntity = quizRepository.findById(questionDTO.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz not found with ID: " + questionDTO.getQuizId()));

        QuestionEntity questionEntity = convertToEntity(questionDTO, quizEntity);
        questionEntity = questionRepository.save(questionEntity);
        return convertToDTO(questionEntity);
    }

    @Override
    public QuestionDTO updateQuestion(Integer id, QuestionDTO questionDTO) {
        QuestionEntity existingQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with ID: " + id));

        QuizEntity quizEntity = quizRepository.findById(questionDTO.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz not found with ID: " + questionDTO.getQuizId()));

        existingQuestion.setQuestionText(questionDTO.getQuestionText());
        existingQuestion.setIsRequired(questionDTO.getIsRequired());
        existingQuestion.setQuiz(quizEntity);

        QuestionEntity updatedQuestion = questionRepository.save(existingQuestion);
        return convertToDTO(updatedQuestion);
    }

    @Override
    public void deleteQuestion(Integer id) {
        QuestionEntity questionEntity = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with ID: " + id));
        questionRepository.delete(questionEntity);
    }

    public QuestionDTO convertToDTO(QuestionEntity questionEntity) {
        return new QuestionDTO(
                questionEntity.getQuestionId(),
                questionEntity.getQuiz().getQuizId(),
                questionEntity.getQuestionText(),
                questionEntity.getIsRequired()
        );
    }
    public QuestionEntity convertToEntity(QuestionDTO questionDTO, QuizEntity quizEntity) {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setQuestionId(questionDTO.getQuestionId());
        questionEntity.setQuiz(quizEntity);
        questionEntity.setQuestionText(questionDTO.getQuestionText());
        questionEntity.setIsRequired(questionDTO.getIsRequired());

        return questionEntity;
    }
}
