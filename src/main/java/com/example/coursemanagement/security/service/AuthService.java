package com.example.coursemanagement.security.service;

import com.example.coursemanagement.security.model.AuthenticationRequest;
import com.example.coursemanagement.security.model.AuthenticationResponse;

public interface AuthService {
    AuthenticationResponse login(AuthenticationRequest authenticationRequest);

}
