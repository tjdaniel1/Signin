package com.example.auth.global.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtUtilTest {
    private JwtUtil jwtUtil = new JwtUtil("ajklflkajdfnkaslkmfaklsd", 1000 * 60l);

    @Test
    void generateToken() {
        //given
        String email = "a@a.com";
        //when
        String token = jwtUtil.generateToken(email);
        //then
        assertNotNull(token);
        assertEquals(3, token.split("\\.").length);
    }
}