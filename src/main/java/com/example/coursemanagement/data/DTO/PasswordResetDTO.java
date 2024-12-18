package com.example.coursemanagement.data.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordResetDTO {
    private String email;
    private String otp; // OTP từ email
    private String newPassword;
    private String confirmPassword;// Mật khẩu mới
}


