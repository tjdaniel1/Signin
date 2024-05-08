package com.example.auth.service;

import com.example.auth.domain.request.SigninRequest;
import com.example.auth.domain.request.SignupRequest;
import com.example.auth.domain.response.SignInResponse;

public interface AuthService {
    void insertUser(SignupRequest request);
    SignInResponse signIn(SigninRequest request);
}
