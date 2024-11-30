package com.example.coursemanagement.api;


import com.example.coursemanagement.constant.ApiMessage;
import com.example.coursemanagement.data.DTO.SubmissionHistoryDTO;
import com.example.coursemanagement.data.mgt.ResponseObject;
import com.example.coursemanagement.service.SubmissionHistoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/history")
@CrossOrigin(origins = "*")
public class SubmissionHistoryApi {
    @Autowired
    private SubmissionHistoryService submissionHistoryService;

    @GetMapping("/getAllSubmissionHistory")
    public ResponseObject<?> doGetAllSubmissionHistory() {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(submissionHistoryService.getAllSubmissionHistories());
            resultApi.setSuccess(true);
            resultApi.setMessage("getAllSubmissionHistory success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/history/getAllSubmissionHistory ", e);
        }
        return resultApi;
    }
    @PostMapping("/saveSubmissionHistory")
    public ResponseObject<?> doPostSaveSubmissionHistory(@Valid @RequestBody SubmissionHistoryDTO submissionHistoryDTO) {
        var resultApi = new ResponseObject<>();
        try {
            var addedCourse = submissionHistoryService.saveSubmissionHistory(submissionHistoryDTO);
            resultApi.setData(addedCourse);
            resultApi.setSuccess(true);
            resultApi.setMessage(ApiMessage.BasicMessageApi.SUCCESS.getBasicMessageApi());
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(ApiMessage.BasicMessageApi.FAIL.getBasicMessageApi());
            log.error("Fail When Call API /api/v1/history/saveSubmissionHistory", e);
        }
        return resultApi;
    }
    @GetMapping("/getSubmissionHistoryById")
    public ResponseObject<?> getSubmissionHistoryById(@RequestParam Integer submissionHistoryId) {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(submissionHistoryService.getSubmissionHistoryById(submissionHistoryId));
            resultApi.setSuccess(true);
            resultApi.setMessage("getSubmissionHistoryById success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/history/getSubmissionHistoryById", e);
        }
        return resultApi;
    }

    @GetMapping("/getSubmissionHistoriesByModuleId")
    public ResponseObject<?> getSubmissionHistoriesByModuleId(@RequestParam Integer moduleId) {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(submissionHistoryService.getSubmissionHistoriesByModuleId(moduleId));
            resultApi.setSuccess(true);
            resultApi.setMessage("getSubmissionHistoriesByModuleId success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/history/getSubmissionHistoriesByModuleId", e);
        }
        return resultApi;
    }

    @GetMapping("/getSubmissionHistoriesByUserIdAndCourseId")
    public ResponseObject<?> getSubmissionHistoriesByUserIdAndCourseId(@RequestParam Integer userId, @RequestParam Integer courseId) {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(submissionHistoryService.getSubmissionHistoriesByUserIdAndCourseId(userId, courseId));
            resultApi.setSuccess(true);
            resultApi.setMessage("getSubmissionHistoriesByUserIdAndCourseId success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/history/getSubmissionHistoriesByUserIdAndCourseId", e);
        }
        return resultApi;
    }

}
