package com.example.coursemanagement.api;

import com.example.coursemanagement.constant.ApiMessage;
import com.example.coursemanagement.data.DTO.UserProgressDTO;
import com.example.coursemanagement.data.mgt.ResponseObject;
import com.example.coursemanagement.service.UserProgressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/user-progress")
@CrossOrigin(origins = "*")
public class UserProgressApi {

    @Autowired
    private UserProgressService userProgressService;

    @GetMapping("/getAllUserProgress")
    public ResponseObject<List<UserProgressDTO>> doGetAllUserProgress() {
        var resultApi = new ResponseObject<List<UserProgressDTO>>();
        try {
            resultApi.setData(userProgressService.getAllUserProgress());
            resultApi.setSuccess(true);
            resultApi.setMessage("Lấy tất cả tiến trình học thành công");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Thất bại khi gọi API /api/v1/user-progress/getAllUserProgress", e);
        }
        return resultApi;
    }

    @GetMapping("/getUserProgressById")
    public ResponseObject<UserProgressDTO> doGetUserProgressById(@RequestParam Integer progressId) {
        var resultApi = new ResponseObject<UserProgressDTO>();
        try {
            UserProgressDTO userProgressDTO = userProgressService.getUserProgressById(progressId);
            resultApi.setData(userProgressDTO);
            resultApi.setSuccess(true);
            resultApi.setMessage(ApiMessage.BasicMessageApi.SUCCESS.getBasicMessageApi());
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage("Không tìm thấy tiến trình học với ID này");
            log.error("Thất bại khi gọi API /api/v1/user-progress/getUserProgressById/{}: ", progressId, e);
        }
        return resultApi;
    }

    @PostMapping("/addUserProgress")
    public ResponseObject<?> doAddUserProgress(@RequestBody UserProgressDTO userProgressDTO) {
        var resultApi = new ResponseObject<UserProgressDTO>();
        try {
            UserProgressDTO savedUserProgress = userProgressService.addUserProgress(userProgressDTO);
            resultApi.setData(savedUserProgress);
            resultApi.setSuccess(true);
            resultApi.setMessage("Thêm tiến trình học thành công");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Thất bại khi gọi API /api/v1/user-progress/addUserProgress", e);
        }
        return resultApi;
    }

    @PutMapping("/updateUserProgress/{id}")
    public ResponseObject<UserProgressDTO> doUpdateUserProgress(@PathVariable Integer id, @RequestBody UserProgressDTO userProgressDTO) {
        var resultApi = new ResponseObject<UserProgressDTO>();
        try {
            UserProgressDTO updatedUserProgress = userProgressService.updateUserProgress(id, userProgressDTO);
            resultApi.setData(updatedUserProgress);
            resultApi.setSuccess(true);
            resultApi.setMessage("Cập nhật tiến trình học thành công");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Thất bại khi gọi API /api/v1/user-progress/updateUserProgress/{id}", e);
        }
        return resultApi;
    }

    @DeleteMapping("/deleteUserProgress/{id}")
    public ResponseObject<Void> doDeleteUserProgress(@PathVariable Integer id) {
        var resultApi = new ResponseObject<Void>();
        try {
            userProgressService.deleteUserProgress(id);
            resultApi.setSuccess(true);
            resultApi.setMessage("Xóa tiến trình học thành công");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Thất bại khi gọi API /api/v1/user-progress/deleteUserProgress/{id}", e);
        }
        return resultApi;
    }

    @GetMapping("/getUserProgressByUserId")
    public ResponseObject<List<UserProgressDTO>> doGetUserProgressByUserId(@RequestParam Integer userId) {
        var resultApi = new ResponseObject<List<UserProgressDTO>>();
        try {
            List<UserProgressDTO> progressList = userProgressService.getUserProgressByUserId(userId);
            resultApi.setData(progressList);
            resultApi.setSuccess(true);
            resultApi.setMessage("Đã tìm thấy tiến trình học cho người dùng này");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Thất bại khi gọi API /api/v1/user-progress/getUserProgressByUserId", e);
        }
        return resultApi;
    }

    @GetMapping("/getUserProgressByUserIdAndCourseId")
    public ResponseObject<List<UserProgressDTO>> doGetUserProgressByUserIdAndCourseId(@RequestParam Integer userId, @RequestParam Integer courseId) {
        var resultApi = new ResponseObject<List<UserProgressDTO>>();
        try {
            List<UserProgressDTO> progressList = userProgressService.getUserProgressByUserIdAndCourseId(userId, courseId);
            resultApi.setData(progressList);
            resultApi.setSuccess(true);
            resultApi.setMessage("Đã tìm thấy tiến trình học cho người dùng và khóa học này");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Thất bại khi gọi API /api/v1/user-progress/getUserProgressByUserIdAndCourseId", e);
        }
        return resultApi;
    }
}
