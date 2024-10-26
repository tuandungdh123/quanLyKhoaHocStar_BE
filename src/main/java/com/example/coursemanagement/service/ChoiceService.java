package com.example.coursemanagement.service;

import com.example.coursemanagement.data.DTO.ChoiceDTO;

import java.util.List;

public interface ChoiceService {
    List<ChoiceDTO> getAllChoices();

    ChoiceDTO getChoiceById(Integer choiceId);

    ChoiceDTO createChoice(ChoiceDTO choiceDTO);

    ChoiceDTO updateChoice(Integer choiceId, ChoiceDTO choiceDTO);

    void deleteChoice(Integer choiceId);
}
