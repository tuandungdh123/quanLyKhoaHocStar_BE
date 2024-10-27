package com.example.coursemanagement.service;

import com.example.coursemanagement.data.DTO.SubmissionHistoryDTO;

import java.util.List;

public interface SubmissionHistoryService {
    List<SubmissionHistoryDTO> getAllSubmissionHistories();

    SubmissionHistoryDTO saveSubmissionHistory(SubmissionHistoryDTO submissionHistoryDTO);

    SubmissionHistoryDTO getSubmissionHistoryById(Integer submissionHistoryId);
}