package com.example.coursemanagement.api;


import com.example.coursemanagement.data.DTO.LessonDTO;
import com.example.coursemanagement.data.mgt.ResponseObject;
import com.example.coursemanagement.service.LessonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/updateLesson/{id}")
    public ResponseObject<LessonDTO> doUpdateLesson(@PathVariable Integer id, @RequestBody LessonDTO lessonDTO) {
        var resultApi = new ResponseObject<LessonDTO>();
        try {
            LessonDTO updatedLesson = lessonService.updateLesson(id, lessonDTO);
            resultApi.setData(updatedLesson);
            resultApi.setSuccess(true);
            resultApi.setMessage("Lesson updated successfully");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/lesson/updateLesson/{id} ", e);
        }
        return resultApi;
    }

    @DeleteMapping("/deleteLesson/{id}")
    public ResponseObject<Void> doDeleteLesson(@PathVariable Integer id) {
        var resultApi = new ResponseObject<Void>();
        try {
            lessonService.deleteLesson(id);
            resultApi.setSuccess(true);
            resultApi.setMessage("Lesson deleted successfully");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/lesson/deleteLesson/{id} ", e);
        }
        return resultApi;
    }
}
