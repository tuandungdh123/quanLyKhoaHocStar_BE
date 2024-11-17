package com.example.coursemanagement.api;

import com.example.coursemanagement.data.DTO.PasswordChangeDTO;
import com.example.coursemanagement.data.DTO.UserDTO;
import com.example.coursemanagement.data.DTO.VerifyOtpRequestDTO;
import com.example.coursemanagement.data.mgt.ResponseObject;
import com.example.coursemanagement.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user-api")
public class UserApi {
    @Autowired
    private UserService userService;

    @GetMapping("/getAllUser")
    public ResponseObject<?> doGetAllUser() {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(userService.getAllUser());
            resultApi.setSuccess(true);
            resultApi.setMessage("getAllUser success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API user-api/getAllUser ", e);
        }
        return resultApi;
    }

    @GetMapping("/getUserByUserId")
    public ResponseObject<?> doGetUserByUserId(@RequestParam("userId") Integer userId){
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(userService.getAllUserByUserId(userId));
            resultApi.setSuccess(true);
            resultApi.setMessage("getAllUser success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API user-api/getAllUser ", e);
        }
        return resultApi;
    }

    @PostMapping("/register")
    public ResponseObject<?> registerUser(@RequestBody UserDTO userDTO) {
        var resultApi = new ResponseObject<>();
        try {
            userService.registerUser(userDTO);
            resultApi.setSuccess(true);
            resultApi.setMessage("Registration successful");
            resultApi.setData(userDTO);

        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API user-api/register ", e);
        }
        return resultApi;
    }

    @PostMapping("/verify-otp")
    public ResponseObject<?> verifyOtp(@RequestBody VerifyOtpRequestDTO request) {
        String email = request.getEmail();
        String otp = request.getOtp();

        var resultApi = new ResponseObject<>();
        if (userService.verifyOtp(email, otp)) {
            resultApi.setSuccess(true);
            resultApi.setMessage("OTP verification successful.");
        } else {
            resultApi.setSuccess(false);
            resultApi.setMessage("Invalid OTP or OTP has expired.");
        }
        return resultApi;
    }

    @PostMapping("/login")
    public ResponseObject<?> loginUser(@RequestBody UserDTO userDTO) {
        var resultApi = new ResponseObject<>();
        try {
            UserDTO loggedInUser = userService.loginUser(userDTO);
            resultApi.setSuccess(true);
            resultApi.setData(loggedInUser);
            resultApi.setMessage("Login successful");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API user-api/login ", e);
        }
        return resultApi;
    }

    @PutMapping("/update")
    public ResponseObject<?> updateUser(@RequestBody UserDTO userDTO) {
        var resultApi = new ResponseObject<>();
        try {
            userService.updateUser(userDTO);
            resultApi.setSuccess(true);
            resultApi.setMessage("User updated successfully");
            resultApi.setData(userDTO);
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API user-api/update ", e);
        }
        return resultApi;
    }

    @PostMapping("/changePassword")
    public ResponseObject<?> changePassword(@RequestBody PasswordChangeDTO passwordChangeDTO) {
        var resultApi = new ResponseObject<>();
        try {
            userService.changePassword(passwordChangeDTO);
            resultApi.setSuccess(true);
            resultApi.setMessage("Password changed successfully");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API user-api/changePassword", e);
        }
        return resultApi;
    }
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Long>> getUserRegistrationStatisticsByMonth() {
        Map<String, Long> statistics = userService.getUserRegistrationStatisticsByMonth();
        return ResponseEntity.ok(statistics);
    }
}
