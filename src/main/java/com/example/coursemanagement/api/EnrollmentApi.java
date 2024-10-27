package com.example.coursemanagement.api;

import com.example.coursemanagement.data.DTO.EnrollmentDTO;
import com.example.coursemanagement.data.mgt.ResponseObject;
import com.example.coursemanagement.service.EnrollmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/enrollment")
@CrossOrigin(origins = "*")
public class EnrollmentApi {

    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping("/getAll")
    public ResponseObject<?> getAllEnrollments() {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(enrollmentService.getAllEnrollments());
            resultApi.setSuccess(true);
            resultApi.setMessage("Fetched all enrollments successfully");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Error in /api/v1/enrollment/getAll", e);
        }
        return resultApi;
    }

    @GetMapping("/getEnrollmentById")
    public ResponseObject<?> getEnrollmentById(@RequestParam("enrollmentId") Integer enrollmentId) {
        var resultApi = new ResponseObject<>();
        try {
            EnrollmentDTO enrollmentDTO = enrollmentService.getEnrollmentById(enrollmentId);
            resultApi.setData(enrollmentDTO);
            resultApi.setSuccess(true);
            resultApi.setMessage("Fetched enrollment successfully");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Failed to get enrollment by ID: {}", enrollmentId, e);
        }
        return resultApi;
    }

    @PutMapping("/updateStatus")
    public ResponseObject<?> updateEnrollmentStatus(@RequestBody EnrollmentDTO enrollmentDTO) {
        var resultApi = new ResponseObject<>();
        try {
            EnrollmentDTO updatedEnrollment = enrollmentService.updateEnrollmentStatus(enrollmentDTO);
            resultApi.setData(updatedEnrollment);
            resultApi.setSuccess(true);
            resultApi.setMessage("Enrollment status updated successfully");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Failed to update enrollment status", e);
        }
        return resultApi;
    }

    @GetMapping("/getEnrollmentByCourseId")
    public ResponseObject<?> getEnrollmentsByCourseId(@RequestParam("courseId") Integer courseId) {
        var resultApi = new ResponseObject<>();
        try {
            var enrollments = enrollmentService.getEnrollmentsByCourseId(courseId);
            resultApi.setData(enrollments);
            resultApi.setSuccess(true);
            resultApi.setMessage("Fetched enrollments for course successfully");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Failed to get enrollments by course ID: {}", courseId, e);
        }
        return resultApi;
    }

    @PostMapping("/addEnrollment")
    public ResponseObject<?> addEnrollment(@RequestBody EnrollmentDTO enrollmentDTO) {
        var resultApi = new ResponseObject<>();
        try {
            EnrollmentDTO newEnrollment = enrollmentService.addEnrollment(enrollmentDTO);
            resultApi.setData(newEnrollment);
            resultApi.setSuccess(true);
            resultApi.setMessage("Enrollment added successfully");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Failed to add enrollment", e);
        }
        return resultApi;
    }
}
