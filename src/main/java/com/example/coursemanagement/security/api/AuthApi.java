package com.example.coursemanagement.security.api;

import com.example.coursemanagement.security.model.AuthenticationRequest;
import com.example.coursemanagement.security.model.AuthenticationResponse;
import com.example.coursemanagement.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@CrossOrigin
public class AuthApi {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(authService.login(authenticationRequest));
    }

}
