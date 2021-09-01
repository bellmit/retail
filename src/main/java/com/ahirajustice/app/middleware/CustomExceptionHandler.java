package com.ahirajustice.app.middleware;

import com.ahirajustice.app.exceptions.ApplicationDomainException;
import com.ahirajustice.app.exceptions.SystemErrorException;
import com.ahirajustice.app.viewmodels.error.ErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) {
        if (ex instanceof ApplicationDomainException) {
            return handleApplicationDomainException((ApplicationDomainException) ex);
        } else {
            SystemErrorException result = new SystemErrorException();
            return new ResponseEntity<ErrorResponse>(result.toErrorResponse(),
                    HttpStatus.valueOf(result.getStatusCode()));
        }
    }

    private ResponseEntity<ErrorResponse> handleApplicationDomainException(ApplicationDomainException ex) {
        return new ResponseEntity<ErrorResponse>(ex.toErrorResponse(), HttpStatus.valueOf(ex.getStatusCode()));
    }

}
