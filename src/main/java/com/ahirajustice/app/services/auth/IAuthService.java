package com.ahirajustice.app.services.auth;

import com.ahirajustice.app.dtos.auth.AuthToken;
import com.ahirajustice.app.dtos.auth.LoginDto;
import com.ahirajustice.app.viewmodels.auth.LoginResponse;

public interface IAuthService {

    public LoginResponse createAccessToken(LoginDto loginDto);

    public boolean authenticateUser(LoginDto loginDto);

    public AuthToken decodeJwt(String token);

}
