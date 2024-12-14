package com.example.coursemanagement.api;

import com.example.coursemanagement.aws.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private S3Service s3Service;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String key = "certificate/" + file.getOriginalFilename();
            return s3Service.uploadFile(key, file.getInputStream(), file.getSize());
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi upload file: " + e.getMessage());
        }
    }
}
