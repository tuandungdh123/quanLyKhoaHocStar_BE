package com.example.coursemanagement.security;

import java.security.SecureRandom;

public class OtpUtil {
    private static final SecureRandom random = new SecureRandom();
    private static final int OTP_LENGTH = 6;

    public static String generateOtp() {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10)); // Random số từ 0-9
        }
        return otp.toString();
    }
}
