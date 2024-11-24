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

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String uploadDir = "D:/React/DATN/quanLyKhoaHocStar/src/assets/images/Background/background-course";
            Path upload = Paths.get(uploadDir);
            if (!Files.exists(upload)) {
                Files.createDirectories(upload);
            }
            Path filPath = upload.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), filPath, StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok().body("File uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while uploading the file: " + e.getMessage());
        }
    }

    @PostMapping("/upload_certification")
    public ResponseEntity<String> uploadFileCertification(@RequestParam("file") MultipartFile file) {
        try {
            String uploadDir = "D:/React/DATN/quanLyKhoaHocStar/src/assets/images/Certification";
            Path upload = Paths.get(uploadDir);
            if (!Files.exists(upload)) {
                Files.createDirectories(upload);
            }
            Path filPath = upload.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), filPath, StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok().body("File uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while uploading the file: " + e.getMessage());
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request: " + e.getMessage());
    }

}