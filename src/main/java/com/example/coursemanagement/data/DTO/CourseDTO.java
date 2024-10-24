package com.example.coursemanagement.data.DTO;

import com.example.coursemanagement.data.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDTO {
    private Integer courseId;
    private String title;
    private String description;
    private String imgUrl;
    private LocalDate startDate;
    private LocalDate endDate;
    private String meetingTime;
    private String schedule;
    private Double price;
    private Boolean status;
    private LocalDateTime createdAt;
    private UserEntity instructor;
}
