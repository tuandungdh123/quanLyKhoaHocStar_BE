package com.example.coursemanagement.service.Implement;

import com.example.coursemanagement.service.CertificateEmailService;
import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class CertificateEmailImplement implements CertificateEmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendCertificateEmail(String toEmail, String userName, String courseTitle, byte[] certificateImage) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // Cài đặt thông tin email
            helper.setTo(toEmail);
            helper.setSubject("Chứng nhận hoàn thành khóa học");

            // Nội dung HTML của email
            String htmlContent =
                    "<div style='font-family:Arial, sans-serif; color:#333;'>" +
                            "<h2 style='color: #4CAF50;'>Chứng nhận hoàn thành khóa học</h2>" +
                            "<p>Xin chào, <strong>" + userName + "</strong>,</p>" +
                            "<p>Bạn đã hoàn thành khóa học <strong>" + courseTitle + "</strong>. Xin chúc mừng!</p>" +
                            "<p>Đính kèm là chứng nhận của bạn.</p>" +
                            "<p><img src='cid:certificateImage' alt='Chứng nhận' style='width:100%; max-width:600px;'></p>" +
                            "<hr>" +
                            "<p style='font-size:0.9em;'>Nếu có bất kỳ thắc mắc nào, vui lòng liên hệ với đội ngũ hỗ trợ của chúng tôi.</p>" +
                            "<p>Trân trọng,<br>Đội ngũ hỗ trợ của StarDev</p>" +
                            "</div>";

            helper.setText(htmlContent, true);

            // Đính kèm tệp ảnh dưới dạng CID (Content-ID)
            DataSource dataSource = new ByteArrayDataSource(certificateImage, "image/png");
            helper.addInline("certificateImage", dataSource); // Sử dụng addInline thay vì addAttachment

            // Gửi email
            mailSender.send(message);
            System.out.println("Chứng nhận đã được gửi thành công đến: " + toEmail);

        } catch (MessagingException e) {
            System.err.println("Lỗi khi gửi email: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Lỗi không xác định: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
