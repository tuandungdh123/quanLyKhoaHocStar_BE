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

    @GetMapping("/getAllEnrollment")
    public ResponseObject<?> doGetAllEnrollment() {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(enrollmentService.getAllEnrollments());
            resultApi.setSuccess(true);
            resultApi.setMessage("getAllModule success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/module/getAllCourse ", e);
        }
        return resultApi;
    }
    @PutMapping("/updateStatus/{enrollmentId}")
    public ResponseObject<?> updateEnrollmentStatus(
            @PathVariable Integer enrollmentId,
            @RequestBody EnrollmentDTO enrollmentDTO) {
        var resultApi = new ResponseObject<>();
        try {
            EnrollmentDTO updatedEnrollment = enrollmentService.updateEnrollmentStatus(
                    enrollmentId,
                    enrollmentDTO.getStatus(),
                    enrollmentDTO.getPaymentStatus()
            );
            resultApi.setData(updatedEnrollment);
            resultApi.setSuccess(true);
            resultApi.setMessage("Update status success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Failed to update enrollment status for ID: {}", enrollmentId, e);
        }
        return resultApi;
    }
}
