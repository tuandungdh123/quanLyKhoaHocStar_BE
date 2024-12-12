package com.example.coursemanagement.service;

public interface CertificateEmailService {
//    void sendCertificateWithAttachment(String toEmail, String userName, String courseName);

    void sendCertificateEmail(String toEmail, String userName, String courseTitle, byte[] certificateFile);
}
