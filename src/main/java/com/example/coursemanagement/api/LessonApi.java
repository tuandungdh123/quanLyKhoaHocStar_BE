package com.example.coursemanagement.api;


import com.example.coursemanagement.constant.ApiMessage;
import com.example.coursemanagement.data.DTO.LessonDTO;
import com.example.coursemanagement.data.DTO.ModuleDTO;
import com.example.coursemanagement.data.mgt.ResponseObject;
import com.example.coursemanagement.service.LessonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/lesson")
@CrossOrigin(origins = "*")
public class LessonApi {
    @Autowired
    private LessonService lessonService;

    @GetMapping("/getAllLesson")
    public ResponseObject<?> doGetAllLesson() {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(lessonService.getAllLesson());
            resultApi.setSuccess(true);
            resultApi.setMessage("getAllLesson success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/lesson/getAllLesson ", e);
        }
        return resultApi;
    }

    @GetMapping("/getLessonById")
    public ResponseObject<?> doGetLessonById(@RequestParam Integer lessonId) {
        var resultApi = new ResponseObject<>();
        try {
            LessonDTO lessonDTO = lessonService.getLessonById(lessonId);

            if (lessonDTO != null) {
                resultApi.setData(lessonDTO);
                resultApi.setSuccess(true);
                resultApi.setMessage(ApiMessage.BasicMessageApi.SUCCESS.getBasicMessageApi());
            } else {
                resultApi.setSuccess(false);
                resultApi.setMessage("Lesson not found");
            }
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(ApiMessage.BasicMessageApi.FAIL.getBasicMessageApi());
            log.error("Fail When Call API /api/v1/module/getLessonById/{}: ", lessonId, e);
        }
        return resultApi;
    }

    @GetMapping("/getLessonsByModuleId")
    public ResponseObject<?> doGetLessonsByModuleId(@RequestParam Integer moduleId) {
        var resultApi = new ResponseObject<>();
        try {
            List<LessonDTO> lessons = lessonService.getLessonsByModuleId(moduleId);
            resultApi.setData(lessons);
            resultApi.setSuccess(true);
            resultApi.setMessage("Lessons found");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/lesson/getLessonsByModuleId ", e);
        }
        return resultApi;
    }

    @PostMapping("/addLesson")
    public ResponseObject<LessonDTO> doAddLesson(@RequestBody LessonDTO lessonDTO) {
        var resultApi = new ResponseObject<LessonDTO>();
        try {
            LessonDTO savedLesson = lessonService.addLesson(lessonDTO);
            resultApi.setData(savedLesson);
            resultApi.setSuccess(true);
            resultApi.setMessage("Lesson added successfully");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/lesson/addLesson ", e);
        }
        return resultApi;
    }

    @PutMapping("/updateLesson")
    public ResponseObject<LessonDTO> updateLesson(@RequestParam Integer lessonId, @RequestBody LessonDTO lessonDTO) {
        var resultApi = new ResponseObject<LessonDTO>();
        try {
            LessonDTO updatedLesson = lessonService.updateLesson(lessonId, lessonDTO);
            resultApi.setData(updatedLesson);
            resultApi.setSuccess(true);
            resultApi.setMessage("Lesson updated successfully");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/lesson/updateLesson with lessonId={}: ", lessonId, e);
        }
        return resultApi;
    }

    @DeleteMapping("/deleteLesson")
    public ResponseObject<Void> deleteLesson(@RequestParam Integer lessonId) {
        var resultApi = new ResponseObject<Void>();
        try {
            lessonService.deleteLesson(lessonId);
            resultApi.setSuccess(true);
            resultApi.setMessage("Lesson deleted successfully");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/lesson/deleteLesson with lessonId={}: ", lessonId, e);
        }
        return resultApi;
    }
}
