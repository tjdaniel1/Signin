package com.example.auth.service;

import com.example.auth.domain.entity.User;
import com.example.auth.domain.entity.UserRepository;
import com.example.auth.domain.request.SigninRequest;
import com.example.auth.domain.request.SignupRequest;
import com.example.auth.domain.response.SignInResponse;
import com.example.auth.exception.ExistedUserException;
import com.example.auth.global.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public void insertUser(SignupRequest request) {
        log.info("insert user");
        // 1. 유저가 있는지 찾아보고
        Optional<User> byEmail = userRepository.findByEmail(request.email());
        // 2-1. 있으면 error
//        user.orElseThrow(() -> new IllegalArgumentException("사용자 없음"));
        if(byEmail.isPresent()) throw new ExistedUserException(request.email());
        // 2. 없으면 insert
        String encoded = passwordEncoder.encode(request.password());
        User entity = request.toEntity(encoded);
        log.info(request.toString());
        userRepository.save(entity);
    }

    @Override
    public SignInResponse signIn(SigninRequest request) {
        Optional<User> byEmail = userRepository.findByEmail(request.email());
        if(byEmail.isEmpty()) throw new IllegalArgumentException("이메일 비어있음");
        if(!passwordEncoder.matches(request.password(),
                byEmail.get().getPassword())) throw new IllegalArgumentException("비밀번호 매치 안됨");
        String token = jwtUtil.generateToken(request.email());

        return SignInResponse.from(token);
    }
}
