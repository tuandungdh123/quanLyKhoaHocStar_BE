package com.example.coursemanagement.api;


import com.example.coursemanagement.data.DTO.ModuleDTO;
import com.example.coursemanagement.data.mgt.ResponseObject;
import com.example.coursemanagement.service.CourseService;
import com.example.coursemanagement.service.ModuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/module")
@CrossOrigin(origins = "*")
public class ModuleApi {
    @Autowired
    private ModuleService moduleService;

    @GetMapping("/getAllModule")
    public ResponseObject<?> doGetAllCourse() {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(moduleService.getAllModule());
            resultApi.setSuccess(true);
            resultApi.setMessage("getAllModule success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/module/getAllCourse ", e);
        }
        return resultApi;
    }

    @PostMapping("/addModule")
    public ResponseObject<?> addModule(@RequestBody ModuleDTO moduleDTO) {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(moduleService.addModule(moduleDTO));
            resultApi.setSuccess(true);
            resultApi.setMessage("addModule success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/module/addModule", e);
        }
        return resultApi;
    }

    @DeleteMapping("/deleteModule/{moduleId}")
    public ResponseObject<?> deleteModule(@PathVariable Integer moduleId) {
        var resultApi = new ResponseObject<>();
        try {
            boolean deleted = moduleService.deleteModule(moduleId);
            resultApi.setSuccess(deleted);
            resultApi.setMessage(deleted ? "deleteModule success" : "Module not found");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/module/deleteModule", e);
        }
        return resultApi;
    }

    @PutMapping("/updateModule/{moduleId}")
    public ResponseObject<?> updateModule(@PathVariable Integer moduleId, @RequestBody ModuleDTO moduleDTO) {
        var resultApi = new ResponseObject<>();
        try {
            ModuleDTO updatedModule = moduleService.updateModule(moduleId, moduleDTO);
            if (updatedModule != null) {
                resultApi.setData(updatedModule);
                resultApi.setSuccess(true);
                resultApi.setMessage("updateModule success");
            } else {
                resultApi.setSuccess(false);
                resultApi.setMessage("Module not found");
            }
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/module/updateModule", e);
        }
        return resultApi;
    }
}
