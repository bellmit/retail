package com.ahirajustice.app.controllers;

import com.ahirajustice.app.dtos.auth.LoginDto;
import com.ahirajustice.app.exceptions.UnauthorizedException;
import com.ahirajustice.app.exceptions.ValidationException;
import com.ahirajustice.app.services.auth.IAuthService;
import com.ahirajustice.app.validators.ValidatorUtils;
import com.ahirajustice.app.validators.auth.LoginDtoValidator;
import com.ahirajustice.app.viewmodels.auth.LoginResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    IAuthService authService;

    @RequestMapping(path = "login", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody LoginDto loginDto) throws UnauthorizedException, ValidationException {
        ValidatorUtils<LoginDto> validator = new ValidatorUtils<LoginDto>();
        validator.validate(new LoginDtoValidator(), loginDto);

        LoginResponse token = new LoginResponse();

        if (!authService.authenticateUser(loginDto)) {
            throw new UnauthorizedException("Incorrect username or password");
        }

        token = authService.createAccessToken(loginDto);

        return token;
    }

}
