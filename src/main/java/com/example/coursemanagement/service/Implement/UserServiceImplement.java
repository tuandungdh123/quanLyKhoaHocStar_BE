package com.example.coursemanagement.service.Implement;

import com.example.coursemanagement.data.DTO.UserDTO;
import com.example.coursemanagement.data.entity.RoleEntity;
import com.example.coursemanagement.data.entity.UserEntity;
import com.example.coursemanagement.exception.AppException;
import com.example.coursemanagement.exception.ErrorCode;
import com.example.coursemanagement.repository.UserRepository;
import com.example.coursemanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {
    final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getAllUser() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void registerUser(UserDTO userDTO) {
        // Mã hóa mật khẩu trước khi lưu
        String encodedPassword = passwordEncoder.encode(userDTO.getPasswordHash());
        userDTO.setPasswordHash(encodedPassword);

        UserEntity userEntity = convertToEntity(userDTO);
        userRepository.save(userEntity);
    }

    @Override
    public UserDTO loginUser(UserDTO userDTO) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(userDTO.getEmail());

        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            if (passwordEncoder.matches(userDTO.getPasswordHash(), userEntity.getPasswordHash())) {
                return convertToDto(userEntity);
            } else {
                throw new AppException(ErrorCode.INVALID_CREDENTIALS, "Invalid password");
            }
        } else {
            throw new AppException(ErrorCode.USER_NOT_FOUND, "User not found");
        }
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userDTO.getUserId());
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();

            // Update fields as necessary
            userEntity.setName(userDTO.getName());
            userEntity.setPhone(userDTO.getPhone());
            userEntity.setEmail(userDTO.getEmail());
            userEntity.setAvatarUrl(userDTO.getAvatarUrl());
            userEntity.setStatus(userDTO.getStatus());

            // Optionally update password if provided
            if (userDTO.getPasswordHash() != null && !userDTO.getPasswordHash().isEmpty()) {
                String encodedPassword = passwordEncoder.encode(userDTO.getPasswordHash());
                userEntity.setPasswordHash(encodedPassword);
            }

            // Update role if necessary
            if (userDTO.getRoleId() != null) {
                RoleEntity roleEntity = new RoleEntity();
                roleEntity.setRoleId(userDTO.getRoleId());
                userEntity.setRole(roleEntity);
            }

            userRepository.save(userEntity); // Save updated entity
        } else {
            throw new AppException(ErrorCode.USER_NOT_FOUND, "User not found");
        }
    }

    @Override
    public void changePassword(Integer userId, String oldPassword, String newPassword) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);

        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();

            // Verify the old password
            if (!passwordEncoder.matches(oldPassword, userEntity.getPasswordHash())) {
                throw new AppException(ErrorCode.INVALID_CREDENTIALS, "Current password is incorrect");
            }

            // Encode the new password and update
            String encodedNewPassword = passwordEncoder.encode(newPassword);
            userEntity.setPasswordHash(encodedNewPassword);
            userRepository.save(userEntity);

        } else {
            throw new AppException(ErrorCode.USER_NOT_FOUND, "User not found");
        }
    }

    private UserDTO convertToDto(UserEntity userEntity) {
        return UserDTO.builder()
                .userId(userEntity.getUserId())
                .name(userEntity.getName())
                .phone(userEntity.getPhone())
                .email(userEntity.getEmail())
                .passwordHash(userEntity.getPasswordHash()) // Nếu không cần trả về, có thể bỏ
                .avatarUrl(userEntity.getAvatarUrl())
                .registrationDate(userEntity.getRegistrationDate())
                .status(userEntity.getStatus())
                .roleId(userEntity.getRole() != null ? userEntity.getRole().getRoleId() : null)
                .build();
    }

    private UserEntity convertToEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userDTO.getUserId());
        userEntity.setName(userDTO.getName());
        userEntity.setPhone(userDTO.getPhone());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPasswordHash(userDTO.getPasswordHash());
        userEntity.setAvatarUrl(userDTO.getAvatarUrl());
        userEntity.setRegistrationDate(userDTO.getRegistrationDate());
        userEntity.setStatus(userDTO.getStatus());

        // Thiết lập RoleEntity nếu roleId khác null
        if (userDTO.getRoleId() != null) {
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setRoleId(userDTO.getRoleId());
            userEntity.setRole(roleEntity);
        }

        return userEntity;
    }


}
