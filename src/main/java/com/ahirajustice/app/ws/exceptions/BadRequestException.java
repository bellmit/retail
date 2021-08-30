package com.ahirajustice.app.ws.exceptions;

public class BadRequestException extends ApplicationDomainException {

    public BadRequestException(String message) {
        super(message, "BadRequest", 400);
    }

}
