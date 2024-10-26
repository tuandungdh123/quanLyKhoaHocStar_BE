package com.example.coursemanagement.service.Implement;

import com.example.coursemanagement.data.DTO.QuizDTO;
import com.example.coursemanagement.data.entity.ModuleEntity;
import com.example.coursemanagement.data.entity.QuizEntity;
import com.example.coursemanagement.repository.ModuleRepository;
import com.example.coursemanagement.repository.QuizRepository;
import com.example.coursemanagement.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizServiceImplement implements QuizService {
    private final QuizRepository quizRepository;
    private final ModuleRepository moduleRepository;

    @Override
    public List<QuizDTO> getAllQuiz(){
        List<QuizEntity> courses = quizRepository.findAll();
        return courses.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public QuizDTO convertToDTO(QuizEntity quizEntity) {
        return new QuizDTO(
                quizEntity.getQuizId(),
                quizEntity.getModule().getModuleId(),
                quizEntity.getTitle(),
                quizEntity.getDescription(),
                quizEntity.getCreatedAt()
        );
    }

    @Override
    public QuizDTO getQuizById(Integer quizId) {
        QuizEntity quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz with ID " + quizId + " not found."));
        return convertToDTO(quiz);
    }

    @Override
    public QuizDTO createQuiz(QuizDTO quizDTO) {
        QuizEntity quizEntity = convertToEntity(quizDTO);
        QuizEntity savedQuiz = quizRepository.save(quizEntity);
        return convertToDTO(savedQuiz);
    }

    @Override
    public QuizDTO updateQuiz(Integer quizId, QuizDTO quizDTO) {
        QuizEntity existingQuiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz with ID " + quizId + " not found."));

        existingQuiz.setTitle(quizDTO.getTitle());
        existingQuiz.setDescription(quizDTO.getDescription());

        Optional<ModuleEntity> optionalModule = moduleRepository.findById(quizDTO.getModuleId());
        optionalModule.ifPresent(existingQuiz::setModule);

        QuizEntity updatedQuiz = quizRepository.save(existingQuiz);
        return convertToDTO(updatedQuiz);
    }

    @Override
    public void deleteQuiz(Integer quizId) {
        if (!quizRepository.existsById(quizId)) {
            throw new RuntimeException("Quiz with ID " + quizId + " not found.");
        }
        quizRepository.deleteById(quizId);
    }


    public QuizEntity convertToEntity(QuizDTO quizDTO) {
        QuizEntity quizEntity = new QuizEntity();
        quizEntity.setQuizId(quizDTO.getQuizId());
        quizEntity.setTitle(quizDTO.getTitle());
        quizEntity.setDescription(quizDTO.getDescription());
        quizEntity.setCreatedAt(quizDTO.getCreatedAt());

        Optional<ModuleEntity> optionalModule = moduleRepository.findById(quizDTO.getModuleId());
        if (optionalModule.isPresent()) {
            quizEntity.setModule(optionalModule.get());
        } else {
            throw new RuntimeException("Module with ID " + quizDTO.getModuleId() + " not found.");
        }

        return quizEntity;
    }
}