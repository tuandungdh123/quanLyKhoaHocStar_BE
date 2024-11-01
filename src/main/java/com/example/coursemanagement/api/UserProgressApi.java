package com.example.coursemanagement.api;

import com.example.coursemanagement.data.DTO.UserProgressDTO;
import com.example.coursemanagement.data.mgt.ResponseObject;
import com.example.coursemanagement.service.UserProgressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/user-progress")
public class UserProgressApi {
    @Autowired
    private UserProgressService userProgressService;

    @GetMapping("/getUserProgress/{userId}/{courseId}")
    public ResponseObject<?> getUserProgress(@PathVariable Integer userId, @PathVariable Integer courseId) {
        var resultApi = new ResponseObject<>();
        try {
            UserProgressDTO progress = userProgressService.getUserProgress(userId, courseId);
            resultApi.setData(progress);
            resultApi.setSuccess(true);
            resultApi.setMessage("User progress retrieved successfully.");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API user-progress-api/getUserProgress ", e);
        }
        return resultApi;
    }

    @PostMapping("/updateUserProgress")
    public ResponseObject<?> updateUserProgress(@RequestBody UserProgressDTO userProgressDTO) {
        var resultApi = new ResponseObject<>();
        try {
            userProgressService.updateUserProgress(userProgressDTO);
            resultApi.setSuccess(true);
            resultApi.setMessage("User progress updated successfully.");
            resultApi.setData(userProgressDTO);
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API user-progress-api/updateUserProgress ", e);
        }
        return resultApi;
    }
}
