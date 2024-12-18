package com.example.coursemanagement.service;

import com.example.coursemanagement.data.DTO.PasswordChangeDTO;
import com.example.coursemanagement.data.DTO.PasswordResetDTO;
import com.example.coursemanagement.data.DTO.UserDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    boolean isEmailExist(String email);
    boolean isPhoneExist(String phone);
    List<UserDTO> getAllUser();
    Optional<UserDTO> getAllUserByUserId(Integer userId);
    void registerUser(UserDTO userDTO);
    UserDTO loginUser(UserDTO userDTO);
    void updateUser(UserDTO userDTO);
    void changePassword(PasswordChangeDTO passwordChangeDTO);
    void sendOtpEmailToUser(String email, String otpCode);

    void resetPassword(PasswordResetDTO passwordResetDTO);

    void sendOtpEmail(String toEmail);
    boolean verifyOtp(String email, String otp);
    Map<String, Long> getUserRegistrationStatisticsByMonth();
}
