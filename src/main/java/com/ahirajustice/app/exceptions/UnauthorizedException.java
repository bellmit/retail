package com.ahirajustice.app.exceptions;

public class UnauthorizedException extends ApplicationDomainException {

    public UnauthorizedException(String message) {
        super(message, "Unauthorized", 400);
    }

}
