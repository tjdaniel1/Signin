package com.example.auth.service;

import com.example.auth.domain.entity.User;
import com.example.auth.domain.entity.UserRepository;
import com.example.auth.domain.request.SignupRequest;
import jakarta.transaction.Transactional;
import jdk.jfr.StackTrace;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AuthServiceTest {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;

    @Nested
    class 회원가입{
        @Test
        void 성공(){
            //give
            SignupRequest request = new SignupRequest(
                    "a@b.com",
                    "1234",
                    "ssss",
                    LocalDate.of(2000,4,6),
                    "남");
            long before = userRepository.count();

            //when
            authService.insertUser(request);
            //then
            Optional<User> byEmail = userRepository.findByEmail(request.email());
            assertTrue(byEmail.isPresent());
            assertNotEquals("1234", byEmail.get().getPassword());
        }
        @Test
        void 실패_이미_있는_이메일(){
            //given
            SignupRequest request = new SignupRequest(
                    "a@b.com",
                    "1234",
                    "ssss",
                    LocalDate.of(2000,4,6),
                    "남"
            );
            userRepository.save(User.builder().email("a@b.com").build());

            //when & then
            assertThrows(IllegalArgumentException.class, () -> {
                authService.insertUser(request);
            });
        }
    }
    @Test
    void insertUser() {
    }
}