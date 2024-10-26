package com.example.coursemanagement.api;


import com.example.coursemanagement.data.DTO.QuizDTO;
import com.example.coursemanagement.data.mgt.ResponseObject;
import com.example.coursemanagement.service.CourseService;
import com.example.coursemanagement.service.QuizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/Quiz")
@CrossOrigin(origins = "*")
public class QuizApi {
    @Autowired
    private QuizService quizService;

    @GetMapping("/getAllQuiz")
    public ResponseObject<?> doGetAllCourse() {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(quizService.getAllQuiz());
            resultApi.setSuccess(true);
            resultApi.setMessage("getAllQuiz success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/Quiz/getAllQuiz ", e);
        }
        return resultApi;
    }
    @GetMapping("/{quizId}")
    public ResponseObject<?> getQuizById(@PathVariable Integer quizId) {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(quizService.getQuizById(quizId));
            resultApi.setSuccess(true);
            resultApi.setMessage("getQuizById success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/Quiz/{quizId} ", e);
        }
        return resultApi;
    }

    @PostMapping("/create")
    public ResponseObject<?> createQuiz(@RequestBody QuizDTO quizDTO) {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(quizService.createQuiz(quizDTO));
            resultApi.setSuccess(true);
            resultApi.setMessage("createQuiz success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/Quiz/create ", e);
        }
        return resultApi;
    }

    @PutMapping("/update/{quizId}")
    public ResponseObject<?> updateQuiz(@PathVariable Integer quizId, @RequestBody QuizDTO quizDTO) {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(quizService.updateQuiz(quizId, quizDTO));
            resultApi.setSuccess(true);
            resultApi.setMessage("updateQuiz success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/Quiz/update/{quizId} ", e);
        }
        return resultApi;
    }

    @DeleteMapping("/delete/{quizId}")
    public ResponseObject<?> deleteQuiz(@PathVariable Integer quizId) {
        var resultApi = new ResponseObject<>();
        try {
            quizService.deleteQuiz(quizId);
            resultApi.setSuccess(true);
            resultApi.setMessage("deleteQuiz success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/Quiz/delete/{quizId} ", e);
        }
        return resultApi;
    }
}
