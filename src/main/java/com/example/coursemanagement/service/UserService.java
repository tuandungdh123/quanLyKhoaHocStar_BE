package com.example.coursemanagement.service;

import com.example.coursemanagement.data.DTO.PasswordChangeDTO;
import com.example.coursemanagement.data.DTO.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getAllUser();

    Optional<UserDTO> getAllUserByUserId(Integer userId);

    void registerUser(UserDTO userDTO);
    UserDTO loginUser(UserDTO userDTO);
    void updateUser(UserDTO userDTO);
    void changePassword(PasswordChangeDTO passwordChangeDTO);
    void sendOtpEmail(String toEmail, String otpCode);
    boolean verifyOtp(String email, String otp);
}
