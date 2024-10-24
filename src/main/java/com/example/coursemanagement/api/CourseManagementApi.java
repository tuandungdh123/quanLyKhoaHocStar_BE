package com.example.coursemanagement.api;


import com.example.coursemanagement.data.mgt.ResponseObject;
import com.example.coursemanagement.service.CourseService;
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
    @GetMapping("/getCourseByCourseID")
    public ResponseEntity<?> doGetCourseByCourseID(){
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("status", true);
            result.put("message", "Get Course Success");
            result.put("data", null);
        } catch (Exception e){
            result.put("status", false);
            result.put("message", "Get Course Fail");
            result.put("data", null);
            log.error("Fail When Call API /course-api/getCourseByCourseID ", e);
        }
        return ResponseEntity.ok(result);
    }
    @PostMapping("/postSaveCourse")
    public ResponseEntity<?> doPostSaveCourse(){
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("status", true);
            result.put("message", "Post Save Course Success");
            result.put("data", null);
        } catch (Exception e){
            result.put("status", false);
            result.put("message", "Post Save Course Fail");
            result.put("data", null);
            log.error("Fail When Call API course-api/postSaveCourse ", e);
        }
        return ResponseEntity.ok(result);
    }
}
