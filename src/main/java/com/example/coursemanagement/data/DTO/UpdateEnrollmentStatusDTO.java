package com.example.coursemanagement.data.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateEnrollmentStatusDTO {
    private Integer enrollmentId;
    private String status;
}
