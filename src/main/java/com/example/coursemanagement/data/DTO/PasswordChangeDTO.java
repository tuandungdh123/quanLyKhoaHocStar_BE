package com.example.coursemanagement.data.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordChangeDTO {
    private Integer userId;
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
    private Integer roleId;
}
