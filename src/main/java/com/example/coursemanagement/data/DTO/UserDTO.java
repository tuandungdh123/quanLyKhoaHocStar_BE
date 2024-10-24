package com.example.coursemanagement.data.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Integer userId;
    private String name;
    private String phone;
    private String email;
    private String passwordHash;
    private String avatarUrl = "avatar.png";
    private Integer roleId = 2;
    private Timestamp registrationDate = Timestamp.valueOf(LocalDateTime.now());
    private Boolean status = true;
}
