package com.ahirajustice.app.services.auth;

import com.ahirajustice.app.config.AppConfig;
import com.ahirajustice.app.config.SpringApplicationContext;
import com.ahirajustice.app.dtos.auth.AuthToken;
import com.ahirajustice.app.repositories.IUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

@Service
public class AuthService implements IAuthService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    AppConfig appConfig;

    @Override
    public AuthToken decodeJwt(String token) {
        AuthToken authToken = new AuthToken();

        AppConfig appConfig = (AppConfig) SpringApplicationContext.getBean("appConfig");

        try {
            Claims claims = Jwts.parser().setSigningKey(appConfig.SECRET_KEY).parseClaimsJws(token).getBody();
            authToken.setUsername(claims.getSubject());
            authToken.setExpiry(claims.getExpiration());
        } catch (ExpiredJwtException ex) {
            return authToken;
        }

        return authToken;
    }

}
