package com.example.coursemanagement.api;

import com.example.coursemanagement.data.DTO.LessonCompletionDTO;
import com.example.coursemanagement.data.mgt.ResponseObject;
import com.example.coursemanagement.service.LessonCompletionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/lesson-completion")
public class LessonCompletionApi {
    @Autowired
    private LessonCompletionService lessonCompletionService;

    @PostMapping("/completeLesson")
    public ResponseObject<?> completeLesson(@RequestBody LessonCompletionDTO lessonCompletionDTO) {
        var resultApi = new ResponseObject<>();
        try {
            lessonCompletionService.completeLesson(lessonCompletionDTO);
            resultApi.setSuccess(true);
            resultApi.setMessage("Lesson marked as completed successfully.");
            resultApi.setData(lessonCompletionDTO);
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API lesson-completion-api/completeLesson ", e);
        }
        return resultApi;
    }

    @GetMapping("/getCompletedLessons/{userId}/{courseId}")
    public ResponseObject<?> getCompletedLessons(@PathVariable Integer userId, @PathVariable Integer courseId) {
        var resultApi = new ResponseObject<>();
        try {
            var completedLessons = lessonCompletionService.getCompletedLessons(userId, courseId);
            resultApi.setData(completedLessons);
            resultApi.setSuccess(true);
            resultApi.setMessage("Completed lessons retrieved successfully.");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API lesson-completion-api/getCompletedLessons ", e);
        }
        return resultApi;
    }
}
