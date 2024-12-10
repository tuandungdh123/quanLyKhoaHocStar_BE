package com.example.coursemanagement.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RestController
@RequestMapping("/api/v1/upload")
@CrossOrigin(origins = "*")
public class FileUploadApi {

    // API upload ảnh background-course
    @PostMapping("/upload-background")
    public ResponseEntity<?> uploadBackgroundCourse(@RequestParam("file") MultipartFile file) {
        try {
            // Cập nhật đường dẫn mới cho ảnh background
            String uploadDir = "http://18.176.84.130/background-course";
            Path uploadPath = Paths.get(uploadDir);

            // Kiểm tra và tạo thư mục nếu chưa có
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = file.getOriginalFilename();
            System.out.println("Uploaded file: " + fileName);
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Trả về URL của ảnh background
            String backgroundUrl = "/background-course/" + fileName;
            return ResponseEntity.ok().body(backgroundUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while uploading the file: " + e.getMessage());
        }
    }

    // API upload ảnh avatar
    @PostMapping("/upload-avatar")
    public ResponseEntity<?> uploadAvatar(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file provided");
        }
        if (file == null || file.isEmpty()) {
            System.out.println("No file was uploaded");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file provided");
        }
        try {
            // Cập nhật đường dẫn mới cho ảnh avatar
            String uploadDir = "http://18.176.84.130/Avatar-Account";
            Path uploadPath = Paths.get(uploadDir);

            // Kiểm tra và tạo thư mục nếu chưa có
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = file.getOriginalFilename();
            assert fileName != null;
            System.out.println("Uploaded file: " + fileName);
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Trả về URL của ảnh avatar
            String avatarUrl = "/Avatar-Account/" + fileName;
            return ResponseEntity.ok().body(avatarUrl); // Trả về URL của ảnh
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while uploading the file: " + e.getMessage() + " at " + e.getStackTrace());
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred while processing the request: " + e.getMessage());
    }
}
