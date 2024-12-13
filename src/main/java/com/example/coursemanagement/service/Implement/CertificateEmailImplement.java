package com.example.coursemanagement.service.Implement;

import com.example.coursemanagement.exception.AppException;
import com.example.coursemanagement.exception.ErrorCode;
import com.example.coursemanagement.service.CertificateEmailService;
import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class CertificateEmailImplement implements CertificateEmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendCertificateEmail(String toEmail, String userName, String courseName, byte[] certificateBytes) {
        try {
            // Thiết lập email
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Cài đặt các thông tin cơ bản cho email
            helper.setTo(toEmail);
            helper.setSubject("Chứng chỉ khóa học: " + courseName);
            helper.setText("Chào " + userName + ",\n\nChúng tôi vui mừng thông báo bạn đã hoàn thành khóa học: " + courseName + ".\n\nChứng chỉ của bạn đính kèm trong email này.");

            // Kiểm tra và đính kèm file chứng chỉ
            if (certificateBytes != null && certificateBytes.length > 0) {
                // Tạo ByteArrayDataSource từ byte array
                DataSource dataSource = new ByteArrayDataSource(certificateBytes, "application/pdf");
                helper.addAttachment("certificate_" + userName + ".pdf", dataSource);
            } else {
                throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR, "No certificate available to attach.");
            }

            // Gửi email
            mailSender.send(message);
            System.out.println("Chứng chỉ đã được gửi qua email cho người dùng: " + toEmail);
        } catch (MessagingException e) {
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR, "Failed to send email with certificate: " + e.getMessage());
        }
    }
}
