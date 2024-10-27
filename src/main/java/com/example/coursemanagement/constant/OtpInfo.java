package com.example.coursemanagement.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class OtpInfo {
    private String otp;
    private LocalDateTime expiryTime;

    public OtpInfo(String otp, LocalDateTime expiryTime) {
        this.otp = otp;
        this.expiryTime = expiryTime;
    }

}