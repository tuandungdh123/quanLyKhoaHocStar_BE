package com.example.coursemanagement.service.Implement;

import com.example.coursemanagement.constant.OtpInfo;
import com.example.coursemanagement.data.DTO.PasswordChangeDTO;
import com.example.coursemanagement.data.DTO.UserDTO;
import com.example.coursemanagement.data.entity.RoleEntity;
import com.example.coursemanagement.data.entity.UserEntity;
import com.example.coursemanagement.exception.AppException;
import com.example.coursemanagement.exception.ErrorCode;
import com.example.coursemanagement.repository.UserRepository;
import com.example.coursemanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.coursemanagement.security.OtpUtil.generateOtp;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {
    final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    private final Map<String, OtpInfo> otpStorage = new HashMap<>();

    @Override
    public List<UserDTO> getAllUser() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void registerUser(UserDTO userDTO) {
        String encodedPassword = passwordEncoder.encode(userDTO.getPasswordHash());
        userDTO.setPasswordHash(encodedPassword);

        String otp = generateOtp();
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(5);
        otpStorage.put(userDTO.getEmail(), new OtpInfo(otp, expiryTime));

        sendOtpEmail(userDTO.getEmail(), otp);

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
    public void sendOtpEmail(String toEmail, String otpCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Xác thực mã OTP");
        message.setText("Mã OTP của bạn là: " + otpCode);
        mailSender.send(message);
    }

    @Override
    public boolean verifyOtp(String email, String otp) {
        OtpInfo otpInfo = otpStorage.get(email);

        if (otpInfo == null) {
            return false;
        }

        if (LocalDateTime.now().isAfter(otpInfo.getExpiryTime())) {
            otpStorage.remove(email);
            return false;
        }

        // Kiểm tra nếu OTP khớp
        if (otp.equals(otpInfo.getOtp())) {
            otpStorage.remove(email);  // Xóa OTP sau khi xác minh thành công
            return true;
        }
        return false;
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userDTO.getUserId());
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();

            userEntity.setName(userDTO.getName());
            userEntity.setPhone(userDTO.getPhone());
            userEntity.setEmail(userDTO.getEmail());
            userEntity.setAvatarUrl(userDTO.getAvatarUrl());
            userEntity.setStatus(userDTO.getStatus());

            if (userDTO.getPasswordHash() != null && !userDTO.getPasswordHash().isEmpty()) {
                String encodedPassword = passwordEncoder.encode(userDTO.getPasswordHash());
                userEntity.setPasswordHash(encodedPassword);
            }

            if (userDTO.getRoleId() != null) {
                RoleEntity roleEntity = new RoleEntity();
                roleEntity.setRoleId(userDTO.getRoleId());
                userEntity.setRole(roleEntity);
            }

            userRepository.save(userEntity);
        } else {
            throw new AppException(ErrorCode.USER_NOT_FOUND, "User not found");
        }
    }

    @Override
    public void changePassword(PasswordChangeDTO passwordChangeDTO) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(passwordChangeDTO.getUserId());

        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();

            if (!passwordEncoder.matches(passwordChangeDTO.getCurrentPassword(), userEntity.getPasswordHash())) {
                throw new AppException(ErrorCode.INVALID_CREDENTIALS, "Current password is incorrect");
            }

            if (!passwordChangeDTO.getNewPassword().equals(passwordChangeDTO.getConfirmPassword())) {
                throw new AppException(ErrorCode.PASSWORDS_DO_NOT_MATCH, "New password and confirm password do not match");
            }

            String encodedNewPassword = passwordEncoder.encode(passwordChangeDTO.getNewPassword());
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
//                .passwordHash(userEntity.getPasswordHash())
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

        if (userDTO.getRoleId() != null) {
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setRoleId(userDTO.getRoleId());
            userEntity.setRole(roleEntity);
        }

        return userEntity;
    }


}
