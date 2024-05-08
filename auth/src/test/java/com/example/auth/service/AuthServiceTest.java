package com.example.auth.service;

import com.example.auth.domain.entity.User;
import com.example.auth.domain.entity.UserRepository;
import com.example.auth.domain.request.SigninRequest;
import com.example.auth.domain.request.SignupRequest;
import com.example.auth.domain.response.SignInResponse;
import jakarta.transaction.Transactional;
import jdk.jfr.StackTrace;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Nested
    class 로그인{
        @Test
        void 성공(){
            //given
            User init = User.builder().email("t@t.com")
                    .password(passwordEncoder.encode("1234"))
                    .nickname("tt")
                    .gender("남")
                    .birthday(LocalDate.of(1990, 1, 1))
                    .build();
            userRepository.save(init);
            SigninRequest request = new SigninRequest("t@t.com", "1234");
            //when
            SignInResponse res = authService.signIn(request);
            //then
            assertNotNull(res.token());
            assertEquals(3,res.token().split(".").length);
            assertEquals("Bearer", res.tokenType());
        }
        @Test
        void 실패(){

        }
    }
    @Test
    void signIn() {
    }

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
}