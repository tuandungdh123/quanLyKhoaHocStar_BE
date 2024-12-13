package com.example.coursemanagement.service;

public interface CertificateEmailService {
    void sendCertificateEmail(String toEmail, String userName, String courseTitle, byte[] certificateImage);
}
