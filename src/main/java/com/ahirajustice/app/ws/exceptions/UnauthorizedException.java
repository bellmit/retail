package com.ahirajustice.app.ws.exceptions;

public class UnauthorizedException extends ApplicationDomainException {

    public UnauthorizedException(String message) {
        super(message, "Unauthorized", 400);
    }

}
