package com.example.auth.service;

import com.example.auth.domain.entity.User;
import com.example.auth.domain.entity.UserRepository;
import com.example.auth.domain.request.SignupRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public void insertUser(SignupRequest request) {
        // 1. 유저가 있는지 찾아보고
        Optional<User> byEmail = userRepository.findByEmail(request.email());
        // 2-1. 있으면 error
//        user.orElseThrow(() -> new IllegalArgumentException("사용자 없음"));
        if(byEmail.isPresent()) throw new IllegalArgumentException();
        // 2. 없으면 insert
        String encoded = passwordEncoder.encode(request.password());
        User entity = request.toEntity(encoded);
        userRepository.save(entity);
    }
}
