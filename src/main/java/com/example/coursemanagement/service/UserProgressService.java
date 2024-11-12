package com.example.coursemanagement.service;

import com.example.coursemanagement.data.DTO.UserProgressDTO;

import java.util.List;

public interface UserProgressService {
    List<UserProgressDTO> getAllUserProgress();
    UserProgressDTO addUserProgress(UserProgressDTO userProgressDTO);
    UserProgressDTO updateUserProgress(Integer progressId, UserProgressDTO userProgressDTO);
    void deleteUserProgress(Integer progressId);
    UserProgressDTO getUserProgressById(Integer progressId);
    List<UserProgressDTO> getUserProgressByUserId(Integer userId);
    List<UserProgressDTO> getUserProgressByUserIdAndCourseId(Integer userId, Integer courseId);
}
