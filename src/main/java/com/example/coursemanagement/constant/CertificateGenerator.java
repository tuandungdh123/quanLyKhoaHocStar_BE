package com.example.coursemanagement.constant;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class CertificateGenerator {

    public static String removeVietnameseAccents(String input) {
        String temp = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    public static void generateCertificate(String userName, String courseName, String certificateFilePath) throws DocumentException, IOException {

        userName = removeVietnameseAccents(userName);
        courseName = removeVietnameseAccents(courseName);

        File directory = new File(certificateFilePath).getParentFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }

        Rectangle halfA4 = new Rectangle(PageSize.A4.getWidth(), PageSize.A4.getHeight() / 2);
        Document document = new Document(halfA4);

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(certificateFilePath));

        document.open();

        // **1. Thêm hình nền (Background Image)**
        Image background = Image.getInstance("D:/React/DATN/quanLyKhoaHocStar/src/assets/images/certificate/Certificate-bg.png");

        float imgWidth = background.getWidth();
        float imgHeight = background.getHeight();
        float scaleWidth = halfA4.getWidth() / imgWidth;
        float scaleHeight = halfA4.getHeight() / imgHeight;
        float scaleFactor = Math.min(scaleWidth, scaleHeight);
        background.scalePercent(scaleFactor * 100);
        float xPosition = (halfA4.getWidth() - (imgWidth * scaleFactor)) / 2;
        float yPosition = (halfA4.getHeight() - (imgHeight * scaleFactor)) / 2;
        background.setAbsolutePosition(xPosition, yPosition);
        document.add(background);

        // **2. Định dạng phông chữ và màu sắc**
        BaseFont baseFont = BaseFont.createFont("C:/Windows/Fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        BaseColor customColor = new BaseColor(140, 118, 118); // Màu #8c7676
        Font headerFont = new Font(baseFont, 24, Font.BOLD, customColor);
        Font contentFont = new Font(baseFont, 18, Font.BOLD, customColor);
        Font dateFont = new Font(baseFont, 12, Font.NORMAL, customColor);

        // **3. Thêm tên người nhận**
        Paragraph recipient = new Paragraph(userName, headerFont);
        recipient.setAlignment(Element.ALIGN_CENTER);
        recipient.setSpacingBefore(130);
        document.add(recipient);

        // **4. Thêm tên khóa học**
        Paragraph course = new Paragraph( courseName, contentFont);
        course.setAlignment(Element.ALIGN_CENTER);
        course.setSpacingBefore(30);
        document.add(course);

        // **5. Thêm ngày theo định dạng dd/MM/yyyy và căn phải**
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = sdf.format(new Date());
        Paragraph date = new Paragraph(currentDate, dateFont);
        date.setAlignment(Element.ALIGN_RIGHT); // Căn ngày sang góc phải
        date.setSpacingBefore(56);
        date.setIndentationRight(80);
        document.add(date);

        // Đóng tài liệu
        document.close();
    }

    public static void main(String[] args) {
        try {
            // Đường dẫn file PDF sẽ được lưu vào
            String certificateFilePath = "C:\\Users\\phuct\\DATN\\quanLyKhoaHocStar\\src\\assets\\images\\certificate\\certificate_half_height.pdf";

            // Gọi phương thức tạo chứng chỉ PDF
            generateCertificate("Lê Thanh Tùng", "Kiểm thử dự án trên nền tảng web", certificateFilePath);
            System.out.println("Chứng chỉ PDF đã được tạo thành công tại: " + certificateFilePath);
        } catch (Exception e) {
            System.out.println("Có lỗi xảy ra khi tạo chứng chỉ PDF: " + e.getMessage());
        }
    }
}
