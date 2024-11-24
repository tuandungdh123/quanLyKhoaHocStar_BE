package com.example.coursemanagement.api;

import com.example.coursemanagement.service.CertificateEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1/certificate")
@CrossOrigin(origins = "*")
public class CertificateEmailApi {
    @Autowired
    private CertificateEmailService certificateService;

    @PostMapping("/send")
    public String sendCertificate(
            @RequestParam String toEmail,
            @RequestParam String userName,
            @RequestParam String courseTitle,
            @RequestParam byte[] certificateImage) {

        certificateService.sendCertificateEmail(toEmail, userName, courseTitle, certificateImage);
        return "Email chứng nhận đã được gửi thành công!";
    }
}
