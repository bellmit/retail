package com.ahirajustice.app.ws.viewmodels.error;

import java.util.Dictionary;

public class ValidationErrorResponse extends ErrorResponse {
    
    private Dictionary<String, String> errors;

    public Dictionary<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Dictionary<String, String> errors) {
        this.errors = errors;
    }
}
