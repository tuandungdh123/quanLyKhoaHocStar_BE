package com.example.coursemanagement.api;

import com.example.coursemanagement.data.DTO.PasswordChangeDTO;
import com.example.coursemanagement.data.DTO.UserDTO;
import com.example.coursemanagement.data.DTO.VerifyOtpRequestDTO;
import com.example.coursemanagement.data.entity.UserEntity;
import com.example.coursemanagement.data.mgt.ResponseObject;
import com.example.coursemanagement.exception.AppException;
import com.example.coursemanagement.repository.UserRepository;
import com.example.coursemanagement.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

import static java.rmi.server.LogStream.log;

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
            UserApi.log.error("Fail When Call API user-api/getAllUser ", e);
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
            UserApi.log.error("Fail When Call API user-api/getAllUser ", e);
        }
        return resultApi;
    }

    @GetMapping("/check-phone")
    public ResponseObject<?> checkPhone(@RequestParam String phone) {
        var resultApi = new ResponseObject<>();
        boolean exists = userService.isPhoneExist(phone);
        if (exists) {
            resultApi.setSuccess(false);
            resultApi.setMessage("SĐT đã được đăng ký.");
        } else {
            resultApi.setSuccess(true);
            resultApi.setMessage("SĐT chưa đăng ký.");
        }
        return resultApi;
    }

    @GetMapping("/check-email")
    public ResponseObject<?> checkEmail(@RequestParam String email) {
        var resultApi = new ResponseObject<>();
        boolean exists = userService.isEmailExist(email);
        if (exists) {
            resultApi.setSuccess(false);
            resultApi.setMessage("Email đã được đăng ký.");
        } else {
            resultApi.setSuccess(true);
            resultApi.setMessage("Email chưa đăng ký.");
        }
        return resultApi;
    }

    @PostMapping("/register")
    public ResponseObject<?> registerUser(@RequestBody UserDTO userDTO) {
        var resultApi = new ResponseObject<>();
        try {
            userService.registerUser(userDTO);
            resultApi.setSuccess(true);
            resultApi.setMessage("Đăng ký thành công");
            resultApi.setData(userDTO);
        } catch (AppException appException) {
            resultApi.setSuccess(false);
            resultApi.setMessage(appException.getMessage());
            UserApi.log.error("Đăng ký thất bại: " + appException.getMessage(), appException);
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage("Có lỗi không mong muốn xảy ra.");
            UserApi.log.error("Fail when calling API user-api/register", e);
        }
        return resultApi;
    }

    @PostMapping("/send-otp")
    public ResponseObject<?> sendOtp(@RequestBody Map<String, String> requestBody) {
        var resultApi = new ResponseObject<>();
        try {
            String email = requestBody.get("email");

            if (email == null || !email.matches("^(?:[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:\\\\[\\x01-\\x7F]|[^\\\"])*\")@(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,}|\\[(?:(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?|[a-zA-Z0-9-]*[a-zA-Z0-9]:[\\x01-\\x7F]+)\\])$")) {
                throw new IllegalArgumentException("Email không hợp lệ");
            }

            userService.sendOtpEmail(email);
            resultApi.setSuccess(true);
            resultApi.setMessage("OTP sent successfully.");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            UserApi.log.error("Fail when calling API user-api/send-otp", e);
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
            UserApi.log.error("Fail When Call API user-api/login ", e);
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
            UserApi.log.error("Fail When Call API user-api/update ", e);
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
            UserApi.log.error("Fail When Call API user-api/changePassword", e);
        }
        return resultApi;
    }
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Long>> getUserRegistrationStatisticsByMonth() {
        Map<String, Long> statistics = userService.getUserRegistrationStatisticsByMonth();
        return ResponseEntity.ok(statistics);
    }
}
