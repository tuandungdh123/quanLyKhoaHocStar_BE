package com.example.coursemanagement.data.DTO;



import lombok.*;

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
    private String title;
    private EnrollmentStatus status;
    private LocalDateTime enrollmentDate;
    private PaymentStatus paymentStatus;

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
