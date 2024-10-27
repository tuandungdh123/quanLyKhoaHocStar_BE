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
            resultApi.setMessage("getAllModule success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/module/getAllCourse ", e);
        }
        return resultApi;
    }
    @PostMapping("/saveCourse")
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
            log.error("Failed to add product: ", e);
        }
        return resultApi;
    }
    @GetMapping("/getSubmissionHistoryById")
    public ResponseObject<?> getSubmissionHistoryById(@RequestBody SubmissionHistoryDTO submissionHistoryDTO) {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(submissionHistoryService.getSubmissionHistoryById(submissionHistoryDTO.getHistoryId()));
            resultApi.setSuccess(true);
            resultApi.setMessage("getMeetingScheduleById success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/meeting-schedule/getById", e);
        }
        return resultApi;
    }

}
