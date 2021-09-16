package com.ahirajustice.app.services.auth;

import com.ahirajustice.app.dtos.auth.AuthToken;

public interface IAuthService {

    public AuthToken decodeJwt(String token);

}
