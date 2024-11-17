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
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
    public Optional<UserDTO> getAllUserByUserId(Integer userId) {
        Optional<UserEntity> users = userRepository.findUserByUserId(userId);
        return users.stream().map(this::convertToDto).findFirst();
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
                throw new AppException(ErrorCode.INVALID_CREDENTIALS, "Mật khẩu không chính xác");
            }
        } else {
            throw new AppException(ErrorCode.USER_NOT_FOUND, "Không tìm thấy người dùng");
        }
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
            throw new AppException(ErrorCode.USER_NOT_FOUND, "Không tìm thấy người dùng");
        }
    }

    @Override
    public void changePassword(PasswordChangeDTO passwordChangeDTO) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(passwordChangeDTO.getUserId());

        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();

            if (!passwordEncoder.matches(passwordChangeDTO.getCurrentPassword(), userEntity.getPasswordHash())) {
                throw new AppException(ErrorCode.INVALID_CREDENTIALS, "Mật khẩu hiện tại không chính xác");
            }

            if (!passwordChangeDTO.getNewPassword().equals(passwordChangeDTO.getConfirmPassword())) {
                throw new AppException(ErrorCode.PASSWORDS_DO_NOT_MATCH, "Mật khẩu mới và mật khẩu xác nhận không khớp");
            }

            String encodedNewPassword = passwordEncoder.encode(passwordChangeDTO.getNewPassword());
            userEntity.setPasswordHash(encodedNewPassword);
            userRepository.save(userEntity);
        } else {
            throw new AppException(ErrorCode.USER_NOT_FOUND, "Không tìm thấy người dùng");
        }
    }

    @Override
    public void sendOtpEmail(String toEmail, String otpCode) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("Xác thực mã OTP của bạn");

            String htmlContent =
                    "<div style='font-family:Arial, sans-serif; color:#333;'>" +
                            "<h2 style='color: #4CAF50;'>Mã OTP của bạn</h2>" +
                            "<p>Xin chào,</p>" +
                            "<p>Bạn vừa yêu cầu mã OTP để xác thực tài khoản. Mã OTP của bạn là:</p>" +
                            "<h1 style='text-align:center; color:#4CAF50;'>" + otpCode + "</h1>" +
                            "<p><strong>Lưu ý:</strong> Mã này sẽ hết hạn sau 5 phút.</p>" +
                            "<hr>" +
                            "<p style='font-size:0.9em;'>Nếu bạn không thực hiện yêu cầu này, vui lòng bỏ qua email này.</p>" +
                            "<p>Trân trọng,<br>Đội ngũ hỗ trợ của StarDev</p>" +
                            "</div>";

            helper.setText(htmlContent, true);
            mailSender.send(message);

            System.out.println("OTP đã gửi thành công đến: " + toEmail);
        } catch (MessagingException e) {
            System.err.println("Lỗi khi gửi email: " + e.getMessage());
            e.printStackTrace();
        }
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

        if (otp.equals(otpInfo.getOtp())) {
            otpStorage.remove(email);
            return true;
        }
        return false;
    }

    private UserDTO convertToDto(UserEntity userEntity) {
        return UserDTO.builder()
                .userId(userEntity.getUserId())
                .name(userEntity.getName())
                .phone(userEntity.getPhone())
                .email(userEntity.getEmail())
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

    @Override
    public Map<String, Long> getUserRegistrationStatisticsByMonth() {
        List<Object[]> results = userRepository.countRegistrationsByMonthForRole3();
        Map<String, Long> statistics = new HashMap<>();

        for (Object[] result : results) {
            Integer month = (Integer) result[0];
            Long count = (Long) result[1];
            statistics.put("Month " + month, count);
        }

        return statistics;
    }
}
