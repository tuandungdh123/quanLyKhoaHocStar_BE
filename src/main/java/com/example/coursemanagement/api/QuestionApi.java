package com.example.coursemanagement.api;


import com.example.coursemanagement.data.DTO.QuestionDTO;
import com.example.coursemanagement.data.mgt.ResponseObject;
import com.example.coursemanagement.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/question")
@CrossOrigin(origins = "*")
public class QuestionApi {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/getAllQuestion")
    public ResponseObject<?> doGetAllQuestion() {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(questionService.getAllQuestion());
            resultApi.setSuccess(true);
            resultApi.setMessage("getAllQuestion success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/question/getAllQuestion ", e);
        }
        return resultApi;
    }
    @GetMapping("/{id}")
    public ResponseObject<?> getQuestionById(@PathVariable Integer id) {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(questionService.getQuestionById(id));
            resultApi.setSuccess(true);
            resultApi.setMessage("getQuestionById success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/question/" + id, e);
        }
        return resultApi;
    }

    @PostMapping("/create")
    public ResponseObject<?> createQuestion(@RequestBody QuestionDTO questionDTO) {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(questionService.createQuestion(questionDTO));
            resultApi.setSuccess(true);
            resultApi.setMessage("createQuestion success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/question/create ", e);
        }
        return resultApi;
    }

    @PutMapping("/update/{id}")
    public ResponseObject<?> updateQuestion(
            @PathVariable Integer id, @RequestBody QuestionDTO questionDTO) {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(questionService.updateQuestion(id, questionDTO));
            resultApi.setSuccess(true);
            resultApi.setMessage("updateQuestion success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/question/update/" + id, e);
        }
        return resultApi;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseObject<?> deleteQuestion(@PathVariable Integer id) {
        var resultApi = new ResponseObject<>();
        try {
            questionService.deleteQuestion(id);
            resultApi.setSuccess(true);
            resultApi.setMessage("deleteQuestion success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/question/delete/" + id, e);
        }
        return resultApi;
    }
}
