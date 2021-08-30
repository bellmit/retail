package com.ahirajustice.app.exceptions;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import com.ahirajustice.app.viewmodels.error.ErrorResponse;
import com.ahirajustice.app.viewmodels.error.ValidationErrorResponse;

import org.springframework.validation.FieldError;

public class ValidationException extends ApplicationDomainException {

    private Dictionary<String, String> failures;

    public ValidationException() {
        super("One or more validation failures have occurred", "UnprocessableEntity", 422);
        this.failures = new Hashtable<String, String>();
    }

    public ValidationException(List<FieldError> errors) {
        this();

        for (FieldError error : errors) {
            this.failures.put(error.getField(), error.getDefaultMessage());
        }
    }

    public Dictionary<String, String> getFailures() {
        return failures;
    }

    public void setFailures(Dictionary<String, String> failures) {
        this.failures = failures;
    }

    @Override
    public ErrorResponse toErrorResponse() {
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();

        errorResponse.setCode(getCode());
        errorResponse.setMessage(getMessage());
        errorResponse.setErrors(getFailures());

        return errorResponse;
    }
}
