package com.example.coursemanagement.service.Implement;

import com.example.coursemanagement.data.DTO.MeetingScheduleDTO;
import com.example.coursemanagement.data.DTO.SubmissionHistoryDTO;
import com.example.coursemanagement.data.entity.*;
import com.example.coursemanagement.repository.*;
import com.example.coursemanagement.service.SubmissionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubmissionHistoryServiceImplement implements SubmissionHistoryService {
    @Autowired
    private SubmissionHistoryRepository submissionHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<SubmissionHistoryDTO> getAllSubmissionHistories() {
        List<SubmissionHistoryEntity> entities = submissionHistoryRepository.findAll();
        return entities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SubmissionHistoryDTO saveSubmissionHistory(SubmissionHistoryDTO submissionHistoryDTO) {
        SubmissionHistoryEntity entity = convertToEntity(submissionHistoryDTO);
        SubmissionHistoryEntity savedEntity = submissionHistoryRepository.save(entity);
        return convertToDTO(savedEntity);
    }

    @Override
    public SubmissionHistoryDTO getSubmissionHistoryById(Integer submissionHistoryId) {
        SubmissionHistoryEntity submissionHistoryEntity = submissionHistoryRepository.findById(submissionHistoryId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found with ID: " + submissionHistoryId));
        return convertToDTO(submissionHistoryEntity);
    }


    public SubmissionHistoryDTO convertToDTO(SubmissionHistoryEntity submissionHistoryEntity) {
        SubmissionHistoryDTO dto = new SubmissionHistoryDTO();
        dto.setHistoryId(submissionHistoryEntity.getHistoryId());
        dto.setUserId(submissionHistoryEntity.getUser() != null ? submissionHistoryEntity.getUser().getUserId() : null);
        dto.setCourseId(submissionHistoryEntity.getCourse() != null ? submissionHistoryEntity.getCourse().getCourseId() : null);
        dto.setModuleId(submissionHistoryEntity.getModule() != null ? submissionHistoryEntity.getModule().getModuleId() : null);
        dto.setQuizId(submissionHistoryEntity.getQuiz() != null ? submissionHistoryEntity.getQuiz().getQuizId() : null);
        dto.setQuestionId(submissionHistoryEntity.getQuestion() != null ? submissionHistoryEntity.getQuestion().getQuestionId() : null);
        dto.setScore(submissionHistoryEntity.getScore());
        dto.setAssignmentStatus(submissionHistoryEntity.getAssignmentStatus());
        dto.setCreatedAt(submissionHistoryEntity.getCreatedAt());
        return dto;
    }




    public SubmissionHistoryEntity convertToEntity(SubmissionHistoryDTO submissionHistoryDTO) {
        SubmissionHistoryEntity entity = new SubmissionHistoryEntity();
        entity.setHistoryId(submissionHistoryDTO.getHistoryId());


        Optional<UserEntity> user = userRepository.findById(submissionHistoryDTO.getUserId());
        Optional<CourseEntity> course = courseRepository.findById(submissionHistoryDTO.getCourseId());
        Optional<ModuleEntity> module = moduleRepository.findById(submissionHistoryDTO.getModuleId());
        Optional<QuizEntity> quiz = quizRepository.findById(submissionHistoryDTO.getQuizId());
        Optional<QuestionEntity> question = questionRepository.findById(submissionHistoryDTO.getQuestionId());
        user.ifPresent(entity::setUser);
        course.ifPresent(entity::setCourse);
        module.ifPresent(entity::setModule);
        quiz.ifPresent(entity::setQuiz);
        question.ifPresent(entity::setQuestion);

        entity.setScore(submissionHistoryDTO.getScore());
        entity.setAssignmentStatus(submissionHistoryDTO.getAssignmentStatus());
        entity.setCreatedAt(submissionHistoryDTO.getCreatedAt() != null ? submissionHistoryDTO.getCreatedAt() : LocalDateTime.now());

        return entity;
    }



}
