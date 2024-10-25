package com.example.coursemanagement.data.DTO;


import com.example.coursemanagement.data.entity.CourseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModuleDTO {
    private Integer moduleId;
    private CourseEntity course;
    private String title;
    private Integer orderNumber;
    private LocalDateTime createdAt = LocalDateTime.now();
}
