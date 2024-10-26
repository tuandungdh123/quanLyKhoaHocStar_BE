package com.example.coursemanagement.api;


import com.example.coursemanagement.data.DTO.ChoiceDTO;
import com.example.coursemanagement.data.mgt.ResponseObject;
import com.example.coursemanagement.service.ChoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/choice")
@CrossOrigin(origins = "*")
public class ChoiceApi {
    @Autowired
    private ChoiceService choiceService;

    @GetMapping("/getAllChoices")
    public ResponseObject<?> doGetAllChoices() {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(choiceService.getAllChoices());
            resultApi.setSuccess(true);
            resultApi.setMessage("getAllChoices success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/choice/getAllChoices ", e);
        }
        return resultApi;
    }

    @GetMapping("/{id}")
    public ResponseObject<?> getChoiceById(@PathVariable Integer id) {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(choiceService.getChoiceById(id));
            resultApi.setSuccess(true);
            resultApi.setMessage("getChoiceById success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/choice/" + id, e);
        }
        return resultApi;
    }

    @PostMapping("/create")
    public ResponseObject<?> createChoice(@RequestBody ChoiceDTO choiceDTO) {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(choiceService.createChoice(choiceDTO));
            resultApi.setSuccess(true);
            resultApi.setMessage("createChoice success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/choice/create ", e);
        }
        return resultApi;
    }

    @PutMapping("/update/{id}")
    public ResponseObject<?> updateChoice(
            @PathVariable Integer id, @RequestBody ChoiceDTO choiceDTO) {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(choiceService.updateChoice(id, choiceDTO));
            resultApi.setSuccess(true);
            resultApi.setMessage("updateChoice success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/choice/update/" + id, e);
        }
        return resultApi;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseObject<?> deleteChoice(@PathVariable Integer id) {
        var resultApi = new ResponseObject<>();
        try {
            choiceService.deleteChoice(id);
            resultApi.setSuccess(true);
            resultApi.setMessage("deleteChoice success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/choice/delete/" + id, e);
        }
        return resultApi;
    }
}
