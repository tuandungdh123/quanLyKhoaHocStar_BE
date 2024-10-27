package com.example.coursemanagement.service.Implement;

import com.example.coursemanagement.data.DTO.ChoiceDTO;
import com.example.coursemanagement.data.entity.ChoiceEntity;
import com.example.coursemanagement.data.entity.QuestionEntity;
import com.example.coursemanagement.exception.AppException;
import com.example.coursemanagement.exception.ErrorCode;
import com.example.coursemanagement.repository.ChoiceRepository;
import com.example.coursemanagement.repository.QuestionRepository;
import com.example.coursemanagement.service.ChoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChoiceServiceImplement implements ChoiceService {
    @Autowired
    private final ChoiceRepository choiceRepository;

    @Autowired
    private final QuestionRepository questionRepository;

    @Override
    public List<ChoiceDTO> getAllChoices() {
        List<ChoiceEntity> choices = choiceRepository.findAll();
        return choices.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ChoiceDTO getChoiceById(Integer choiceId) {
        ChoiceEntity entity = choiceRepository.findById(choiceId)
                .orElseThrow(() -> new AppException(ErrorCode.CHOICE_NOT_FOUND, "Choice not found"));
        return toDTO(entity);
    }

    @Override
    public ChoiceDTO createChoice(ChoiceDTO choiceDTO) {
        QuestionEntity question = questionRepository.findById(choiceDTO.getQuestionId())
                .orElseThrow(() -> new AppException(ErrorCode.QUESTION_NOT_FOUND, "Question not found"));

        ChoiceEntity entity = toEntity(choiceDTO, question);
        ChoiceEntity savedEntity = choiceRepository.save(entity);
        return toDTO(savedEntity);
    }

    @Override
    public ChoiceDTO updateChoice(ChoiceDTO choiceDTO) {
        ChoiceEntity existingEntity = choiceRepository.findById(choiceDTO.getChoiceId())
                .orElseThrow(() -> new AppException(ErrorCode.CHOICE_NOT_FOUND, "Choice not found"));

        QuestionEntity question = questionRepository.findById(choiceDTO.getQuestionId())
                .orElseThrow(() -> new AppException(ErrorCode.QUESTION_NOT_FOUND, "Question not found"));

        existingEntity.setChoiceText(choiceDTO.getChoiceText());
        existingEntity.setIsCorrect(choiceDTO.getIsCorrect());
        existingEntity.setQuestion(question);

        ChoiceEntity updatedEntity = choiceRepository.save(existingEntity);
        return toDTO(updatedEntity);
    }

    @Override
    public void deleteChoice(Integer choiceId) {
        ChoiceEntity entity = choiceRepository.findById(choiceId)
                .orElseThrow(() -> new AppException(ErrorCode.CHOICE_NOT_FOUND, "Choice not found"));
        choiceRepository.delete(entity);
    }

    private ChoiceDTO toDTO(ChoiceEntity entity) {
        return ChoiceDTO.builder()
                .choiceId(entity.getChoiceId())
                .questionId(entity.getQuestion().getQuestionId())
                .choiceText(entity.getChoiceText())
                .isCorrect(entity.getIsCorrect())
                .build();
    }

    private ChoiceEntity toEntity(ChoiceDTO dto, QuestionEntity question) {
        return new ChoiceEntity(
                dto.getChoiceId(),
                question,
                dto.getChoiceText(),
                dto.getIsCorrect()
        );
    }
}
