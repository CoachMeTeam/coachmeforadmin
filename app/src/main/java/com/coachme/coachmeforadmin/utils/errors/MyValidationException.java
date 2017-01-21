package com.coachme.coachmeforadmin.utils.errors;


import java.util.ArrayList;
import java.util.List;

public class MyValidationException extends RuntimeException {
    private List<ValidationError> errors;

    public MyValidationException() {
        this.errors = new ArrayList<>();
    }

    public MyValidationException(String message, Exception e) {
        super(message, e);
        this.errors = new ArrayList<>();
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public void addError(int code, String field, String description) {
        ValidationError error = new ValidationError();
        error.setCode(code);
        error.setField(field);
        error.setDescription(description);
        this.errors.add(error);
    }
}

class ValidationError {
    private int code;
    private String field;
    private String description;

    public ValidationError() {
        this.code = 400;
    }

    public ValidationError(String field, String description) {
        this.code = 400;
        this.field = field;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}