package com.example.coursemanagement.data.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingScheduleDTO {
    private Integer meetingId;
    private Integer courseId;
    private LocalDateTime meetingDate;
    private LocalDateTime createdAt;

}
