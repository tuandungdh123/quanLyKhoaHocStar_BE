package com.example.coursemanagement.data.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationStatsDTO {
    private Integer year;
    private Integer month;
    private Integer day;
    private Long userCount;

}

