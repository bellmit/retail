package com.ahirajustice.app.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ahirajustice.app.config.AppConfig;
import com.ahirajustice.app.config.SpringApplicationContext;
import com.ahirajustice.app.constants.SecurityConstants;
import com.ahirajustice.app.dtos.auth.LoginDto;
import com.ahirajustice.app.viewmodels.auth.LoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final Gson gson = new Gson();

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws RuntimeException {
        try {
            LoginDto login = new ObjectMapper().readValue(req.getInputStream(), LoginDto.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword(), new ArrayList<>()));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
            Authentication auth) throws IOException, ServletException {
        AppConfig appConfig = (AppConfig) SpringApplicationContext.getBean("appConfig");

        String username = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();
        String token = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + appConfig.ACCESS_TOKEN_EXPIRE_MINUTES))
                .signWith(SignatureAlgorithm.HS512, appConfig.SECRET_KEY).compact();

        LoginResponse response = new LoginResponse();
        response.setAccessToken(token);
        response.setTokenType(SecurityConstants.TOKEN_PREFIX);
        String responseBody = this.gson.toJson(response);

        PrintWriter writer = res.getWriter();
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        writer.print(responseBody);
        writer.flush();
    }

}
