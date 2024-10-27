package com.example.coursemanagement.api;


import com.example.coursemanagement.constant.ApiMessage;
import com.example.coursemanagement.data.DTO.CourseDTO;
import com.example.coursemanagement.data.DTO.UserDTO;
import com.example.coursemanagement.data.mgt.ResponseObject;
import com.example.coursemanagement.service.CourseService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/course-api")
@CrossOrigin(origins = "*")
public class CourseManagementApi {
    @Autowired
    private CourseService courseService;

    @GetMapping("/getAllCourse")
    public ResponseObject<?> doGetAllCourse() {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(courseService.getAllCourse());
            resultApi.setSuccess(true);
            resultApi.setMessage("getAllUser success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API user-api/getAllUser ", e);
        }
        return resultApi;
    }
    @GetMapping("/getCourseById")
    public ResponseObject<?> doGetCourseById(@RequestParam Integer courseId) {
        var resultApi = new ResponseObject<>();
        try {
            CourseDTO course = courseService.getCourseById(courseId);

            if (course != null) {
                resultApi.setData(course);
                resultApi.setSuccess(true);
                resultApi.setMessage(ApiMessage.BasicMessageApi.SUCCESS.getBasicMessageApi());
            } else {
                resultApi.setSuccess(false);
                resultApi.setMessage("Course not found");
            }
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(ApiMessage.BasicMessageApi.FAIL.getBasicMessageApi());
            log.error("Fail When Call API /user-api/getCourseById/{}: ", courseId, e);
        }
        return resultApi;
    }

    @PostMapping("/saveCourse")
    public ResponseObject<?> doPostSaveUser(@Valid @RequestBody CourseDTO courseDTO) {
        var resultApi = new ResponseObject<>();
        try {
            var addedCourse = courseService.doSaveCourse(courseDTO);
            resultApi.setData(addedCourse);
            resultApi.setSuccess(true);
            resultApi.setMessage(ApiMessage.BasicMessageApi.SUCCESS.getBasicMessageApi());
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(ApiMessage.BasicMessageApi.FAIL.getBasicMessageApi());
            log.error("Failed to add product: ", e);
        }
        return resultApi;
    }

    @DeleteMapping("/deleteCourse/{courseId}")
    public ResponseObject<?> doDeleteCourse(@PathVariable Integer courseId) {
        var resultApi = new ResponseObject<>();
        try {
            courseService.deleteCourseById(courseId);
            resultApi.setSuccess(true);
            resultApi.setMessage("Delete course success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage("Delete course failed: " + e.getMessage());
            log.error("Failed to delete course: ", e);
        }
        return resultApi;
    }

    @PutMapping("/updateCourse")
    public ResponseObject<?> doPutUpdateCourse(@Valid @RequestBody CourseDTO courseDTO) {
        var resultApi = new ResponseObject<>();
        try {
            var updatedCourse = courseService.updateCourse(courseDTO);
            resultApi.setData(updatedCourse);
            resultApi.setSuccess(true);
            resultApi.setMessage(ApiMessage.BasicMessageApi.SUCCESS.getBasicMessageApi());
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(ApiMessage.BasicMessageApi.FAIL.getBasicMessageApi());
            log.error("Failed to update course: ", e);
        }
        return resultApi;
    }
}
