package com.example.coursemanagement.security.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
    private String token;
    private Integer userId;
    private String name;
    private String email;
    private String avatarUrl;
    private Integer roleId;
    private Timestamp registrationDate;
    private Boolean status;
}
