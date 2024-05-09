package com.example.auth.controller;

import com.example.auth.domain.request.SigninRequest;
import com.example.auth.domain.request.SignupRequest;
import com.example.auth.domain.response.SignInResponse;
import com.example.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;


    @PostMapping("signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@RequestBody SignupRequest signupRequest) {
        authService.insertUser(signupRequest);
    }

    @PostMapping("signin")
    @ResponseStatus(HttpStatus.CREATED)
    public SignInResponse signin(@RequestBody SigninRequest signinRequest) {
        return authService.signIn(signinRequest);
    }
}
