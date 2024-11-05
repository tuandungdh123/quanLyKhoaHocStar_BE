package com.example.coursemanagement.service;

import com.example.coursemanagement.data.DTO.UserProgressDTO;

public interface UserProgressService {
    UserProgressDTO getUserProgress(Integer userId, Integer courseId);
    void updateUserProgress(UserProgressDTO userProgressDTO);
}
