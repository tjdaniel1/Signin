package com.example.auth.global.utils;

import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


class JwtUtilTest {
    private final JwtUtil jwtUtil = new JwtUtil("ajklflkajdfnkaslkmfaklsdajklflkajdfnkaslkmfaklsdajklflkajdfnkaslkmfaklsd", 1000l);

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

    @Nested
    class EmailFromTokenAndValidate {
        @Test
        void 성공(){
            //given
            String email = "a@a.com";
            String token = jwtUtil.generateToken(email);
            //when
            String answer = jwtUtil.getByEmailFromTokenAndValidate(token);
            //then
            assertNotNull(answer);
            assertEquals(email,answer);
        }
        @Test
        void 시간_만료() throws InterruptedException{
            //given
            String email = "a@a.com";
            String token = jwtUtil.generateToken(email);
            Thread.sleep(1000l);
            //when
           assertThrows(JwtException.class, () -> jwtUtil.getByEmailFromTokenAndValidate(token));

        }


    }
}