package com.example.coursemanagement.api;

import com.example.coursemanagement.data.DTO.EnrollmentDTO;
import com.example.coursemanagement.data.DTO.UpdateEnrollmentStatusDTO;
import com.example.coursemanagement.data.mgt.ResponseObject;
import com.example.coursemanagement.service.EnrollmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @GetMapping("/get-enrollment-by-user-id-and-course-id")
    public ResponseObject<?> getEnrollmentByUserIdAndCourseId(@RequestParam("userId") Integer userId, @RequestParam("courseId") Integer courseId) {
        var resultApi = new ResponseObject<>();
        try {
            EnrollmentDTO enrollmentDTO = enrollmentService.getEnrollmentByUserIdAndCourseId(userId, courseId);
            resultApi.setData(enrollmentDTO);
            resultApi.setSuccess(true);
            resultApi.setMessage("Fetched enrollment successfully");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Failed to get enrollment by user ID {} and course ID {}: {}", userId, courseId, e.getMessage());
        }
        return resultApi;
    }

    @PutMapping("/update-enrollment-status")
    public ResponseObject<?> updateEnrollmentStatus(@RequestBody UpdateEnrollmentStatusDTO requestData) {
        var resultApi = new ResponseObject<>();
        try {
            UpdateEnrollmentStatusDTO updatedEnrollmentStatus = enrollmentService.updateEnrollmentStatus(requestData);
            resultApi.setData(updatedEnrollmentStatus);
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

    @GetMapping("/getEnrollmentByUserId")
    public ResponseObject<?> getEnrollmentsByUserId(@RequestParam("userId") Integer userId) {
        var resultApi = new ResponseObject<>();
        try {
            var enrollments = enrollmentService.getAllEnrollmentsByUserId(userId);
            resultApi.setData(enrollments);
            resultApi.setSuccess(true);
            resultApi.setMessage("Fetched enrollments for course successfully");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Failed to get enrollments by User ID: {}", userId, e);
        }
        return resultApi;
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Long>> getEnrollmentStatisticsByCoursePrice() {
        Map<String, Long> statistics = enrollmentService.getEnrollmentStatisticsByCoursePrice();
        return ResponseEntity.ok(statistics);
    }

    @GetMapping("/revenue/statistics")
    public ResponseEntity<Map<String, Double>> getMonthlyRevenueStatistics() {
        Map<String, Double> statistics = enrollmentService.getMonthlyRevenueStatistics();
        return ResponseEntity.ok(statistics);
    }

    @GetMapping("/checkEnrollment")
    public ResponseObject<?> checkEnrollment(@RequestParam("userId") Integer userId, @RequestParam("courseId") Integer courseId) {
        var resultApi = new ResponseObject<>();
        try {
            EnrollmentDTO enrollmentDTO = enrollmentService.checkEnrollment(userId, courseId);

            if (enrollmentDTO != null) {
                if (enrollmentDTO.getPaymentStatus() == EnrollmentDTO.PaymentStatus.pending) {
                    resultApi.setSuccess(true);
                    resultApi.setMessage("Enrollment found with pending payment status. Redirecting to payment.");
                } else if (enrollmentDTO.getPaymentStatus() == EnrollmentDTO.PaymentStatus.failed) {
                    resultApi.setSuccess(true);
                    resultApi.setMessage("Payment failed. Redirecting to payment page for retry.");
                } else {
                    resultApi.setSuccess(false);
                    resultApi.setMessage("No pending or failed enrollment. User is not enrolled.");
                }
            } else {
                resultApi.setSuccess(false);
                resultApi.setMessage("No enrollment found for the user.");
            }

            resultApi.setData(enrollmentDTO);
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Failed to check enrollment for userId: {} and courseId: {}", userId, courseId, e);
        }
        return resultApi;
    }

}
