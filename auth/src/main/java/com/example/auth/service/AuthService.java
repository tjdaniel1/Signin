package com.example.auth.service;

import com.example.auth.domain.request.SigninRequest;
import com.example.auth.domain.request.SignupRequest;

public interface AuthService {
    void insertUser(SignupRequest request);
}
