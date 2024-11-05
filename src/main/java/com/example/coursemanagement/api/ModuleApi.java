package com.example.coursemanagement.api;


import com.example.coursemanagement.constant.ApiMessage;
import com.example.coursemanagement.data.DTO.CourseDTO;
import com.example.coursemanagement.data.DTO.ModuleDTO;
import com.example.coursemanagement.data.mgt.ResponseObject;
import com.example.coursemanagement.service.CourseService;
import com.example.coursemanagement.service.ModuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/getModuleById")
    public ResponseObject<?> doGetModuleById(@RequestParam Integer moduleId) {
        var resultApi = new ResponseObject<>();
        try {
            ModuleDTO moduleDTO = moduleService.getModuleById(moduleId);

            if (moduleDTO != null) {
                resultApi.setData(moduleDTO);
                resultApi.setSuccess(true);
                resultApi.setMessage(ApiMessage.BasicMessageApi.SUCCESS.getBasicMessageApi());
            } else {
                resultApi.setSuccess(false);
                resultApi.setMessage("Module not found");
            }
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(ApiMessage.BasicMessageApi.FAIL.getBasicMessageApi());
            log.error("Fail When Call API /api/v1/module/getModuleById/{}: ", moduleId, e);
        }
        return resultApi;
    }

    @GetMapping("/getModulesByCourseId")
    public ResponseObject<?> doGetModulesByCourseId(@RequestParam Integer courseId) {
        var resultApi = new ResponseObject<>();
        try {
            List<ModuleDTO> modules = moduleService.getModulesByCourseId(courseId);
            resultApi.setData(modules);
            resultApi.setSuccess(true);
            resultApi.setMessage("Modules found");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/module/getModulesByCourseId ", e);
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

    @DeleteMapping("/deleteModule")
    public ResponseObject<?> deleteModule(@RequestParam Integer moduleId) {
        var resultApi = new ResponseObject<>();
        try {
            boolean deleted = moduleService.deleteModule(moduleId);
            resultApi.setSuccess(deleted);
            resultApi.setMessage(deleted ? ApiMessage.BasicMessageApi.SUCCESS.getBasicMessageApi() : "Module not found");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/module/deleteModule with moduleId={}: ", moduleId, e);
        }
        return resultApi;
    }

    @PutMapping("/updateModule")
    public ResponseObject<?> updateModule(@RequestParam Integer moduleId, @RequestBody ModuleDTO moduleDTO) {
        var resultApi = new ResponseObject<>();
        try {
            ModuleDTO updatedModule = moduleService.updateModule(moduleId, moduleDTO);
            if (updatedModule != null) {
                resultApi.setData(updatedModule);
                resultApi.setSuccess(true);
                resultApi.setMessage(ApiMessage.BasicMessageApi.SUCCESS.getBasicMessageApi());
            } else {
                resultApi.setSuccess(false);
                resultApi.setMessage("Module not found");
            }
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/module/updateModule with moduleId={}: ", moduleId, e);
        }
        return resultApi;
    }
}
