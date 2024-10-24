package com.example.coursemanagement.service;

import com.example.coursemanagement.data.DTO.PasswordChangeDTO;
import com.example.coursemanagement.data.DTO.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUser();
    void registerUser(UserDTO userDTO);
    UserDTO loginUser(UserDTO userDTO);
    void updateUser(UserDTO userDTO);
    void changePassword(PasswordChangeDTO passwordChangeDTO);
}
