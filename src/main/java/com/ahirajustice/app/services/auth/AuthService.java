package com.ahirajustice.app.services.auth;

import java.util.Date;
import java.util.Optional;

import com.ahirajustice.app.common.CommonHelper;
import com.ahirajustice.app.config.AppConfig;
import com.ahirajustice.app.config.SpringApplicationContext;
import com.ahirajustice.app.constants.SecurityConstants;
import com.ahirajustice.app.dtos.auth.AuthToken;
import com.ahirajustice.app.dtos.auth.LoginDto;
import com.ahirajustice.app.entities.User;
import com.ahirajustice.app.enums.TimeFactor;
import com.ahirajustice.app.repositories.IUserRepository;
import com.ahirajustice.app.viewmodels.auth.LoginResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AuthService implements IAuthService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    AppConfig appConfig;

    @Override
    public LoginResponse createAccessToken(LoginDto loginDto) {
        String subject = loginDto.getEmail();
        int expiry = loginDto.getExpires() > 0 ? loginDto.getExpires() : appConfig.ACCESS_TOKEN_EXPIRE_MINUTES;

        String token = Jwts.builder()
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + CommonHelper.convertToMillis(expiry, TimeFactor.MINUTES)))
                .signWith(SignatureAlgorithm.HS512, appConfig.SECRET_KEY).compact();

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(token);
        loginResponse.setTokenType(SecurityConstants.TOKEN_PREFIX);

        return loginResponse;
    }

    @Override
    public boolean authenticateUser(LoginDto loginDto) {
        Optional<User> userExists = userRepository.findByEmail(loginDto.getEmail());

        if (!userExists.isPresent()) {
            return false;
        }

        if (!verifyPassword(loginDto.getPassword(), userExists.get().getEncryptedPassword())) {
            return false;
        }

        return true;
    }

    @Override
    public AuthToken decodeJwt(String token) {
        AuthToken authToken = new AuthToken();

        AppConfig appConfig = (AppConfig) SpringApplicationContext.getBean("appConfig");

        try{
            Claims claims = Jwts.parser().setSigningKey(appConfig.SECRET_KEY).parseClaimsJws(token).getBody();
            authToken.setUsername(claims.getSubject());
            authToken.setExpiry(claims.getExpiration());
        }
        catch(ExpiredJwtException ex){
            return authToken;
        }

        return authToken;
    }

    private boolean verifyPassword(String password, String encryptedPassword) {
        return passwordEncoder.matches(password, encryptedPassword);
    }

}
