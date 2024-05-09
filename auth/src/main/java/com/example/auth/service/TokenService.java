package com.example.auth.service;


import com.example.auth.domain.request.TeamRequest;

public interface TokenService {
    boolean isAuthenticatedTeam(TeamRequest request);

}
