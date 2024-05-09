package com.example.auth.global.config;

import com.example.auth.global.utils.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CustomSecurityConfig {
    private final UserDetailsService authService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(c -> c.configurationSource(request -> {
            var corsConfiguration = new CorsConfiguration();
            corsConfiguration.addAllowedHeader("*");
            corsConfiguration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
            corsConfiguration.setAllowedOrigins(List.of("*"));
            return corsConfiguration;
                }));
        http.userDetailsService(authService);
            http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
            http.authorizeHttpRequests(req ->
                    req.requestMatchers("/api/v1/auth/signin", "/api/v1/auth/signup")
                            .permitAll()
                            .anyRequest().authenticated()
                    );
            return http.build();
    }
}
