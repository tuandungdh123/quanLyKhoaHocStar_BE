package com.example.coursemanagement.data.DTO;



import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollmentDTO {
    private Integer enrollmentId;
    private Integer userId;
    private String userName;
    private Integer courseId;
    private String courseName;
    private String description;
    private String title;
    private String imgUrl;
    private EnrollmentStatus status;
    private LocalDateTime enrollmentDate = Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime();
    private PaymentStatus paymentStatus;
    private String certificateUrl;

    public enum EnrollmentStatus {
        completed,
        in_progress
    }

    public enum PaymentStatus {
        pending,
        completed,
        failed
    }
}
