package com.coachme.coachmeforadmin.utils.errors;


public class NotFoundException extends RuntimeException {
    private NotFoundError error;

    public NotFoundException(String message, String description) {
        this.error = new NotFoundError(message, description);
    }

    public NotFoundError getError() {
        return error;
    }

    public void setError(NotFoundError error) {
        this.error = error;
    }
}

class NotFoundError {
    private int code;
    private String message;
    private String description;

    public NotFoundError() {
        this.code = 404;
    }

    public NotFoundError(String message, String description) {
        this.code = 404;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
