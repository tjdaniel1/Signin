package com.example.auth.domain.request;

import java.time.LocalDate;

public record SigninRequest(
        String email,
        String password
) {

}
