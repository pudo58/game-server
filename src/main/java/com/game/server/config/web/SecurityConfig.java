package com.game.server.config.web;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig{
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();
        http
                .authorizeHttpRequests((requests) -> requests
                        .anyRequest().permitAll());
        return http.build();
    }
}
