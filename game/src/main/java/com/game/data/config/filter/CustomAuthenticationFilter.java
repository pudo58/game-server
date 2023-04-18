package com.game.data.config.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.MimeTypeUtils;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            Scanner sc = new Scanner(request.getReader());
            StringBuilder sb = new StringBuilder();
            while (sc.hasNext()) {
                sb.append(sc.nextLine());
            }
            JSONObject jsonObject = new JSONObject(sb.toString());
            String username = jsonObject.getString("username");
            String password = jsonObject.getString("password");
            log.info("username: " + username);
            log.info("password: " + password);
            UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(username, password);
            // skip password encoder
            authReq.setDetails(authenticationDetailsSource.buildDetails(request));
            return authenticationManager.authenticate(authReq);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("vHPxbmyhXq".getBytes(StandardCharsets.UTF_8));
        String accessToken = JWT.create().
                withSubject(userDetails.getUsername()).
                withExpiresAt(new java.util.Date(System.currentTimeMillis() + 30 * 600 * 1000)).
                withIssuer(request.getRequestURL().toString()).
                withClaim("role", userDetails.getAuthorities().iterator().next().getAuthority()).
                sign(algorithm);
        String refreshToken = JWT.create().
                withSubject(userDetails.getUsername()).
                withExpiresAt(new java.util.Date(System.currentTimeMillis() + 90 * 600 * 1000)).
                withIssuer(request.getRequestURL().toString()).
                withClaim("roles", userDetails.getAuthorities().iterator().next().getAuthority()).
                sign(algorithm);
        response.setHeader("refresh_token", refreshToken);
        response.setHeader("Authorization", accessToken);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);
        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        // OAuth0
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }

}
