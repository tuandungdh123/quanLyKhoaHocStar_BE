package com.example.coursemanagement.security.service.impl;

import com.example.coursemanagement.security.jwt.JwtTokenProvider;
import com.example.coursemanagement.security.model.AuthenticationRequest;
import com.example.coursemanagement.security.model.AuthenticationResponse;
import com.example.coursemanagement.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateJwtToken(authentication);

        // Lấy thông tin từ UserDetails
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Trả về các thông tin người dùng cùng với token
        return AuthenticationResponse.builder()
                .token(token)
                .userId(userDetails.getUserId())  // userId đã được cung cấp trong UserDetailsImpl
                .name(userDetails.getName())      // tên người dùng
                .email(userDetails.getUsername()) // email người dùng
                .avatarUrl(userDetails.getAvatarUrl()) // avatarUrl
                .roleId(userDetails.getRoleId()) // roleId
                .registrationDate(userDetails.getRegistrationDate()) // registrationDate
                .status(userDetails.getStatus()) // status
                .build();
    }


}
