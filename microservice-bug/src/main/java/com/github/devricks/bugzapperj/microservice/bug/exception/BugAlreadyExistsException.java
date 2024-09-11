package com.github.devricks.bugzapperj.microservice.bug.exception;

public class BugAlreadyExistsException extends RuntimeException {
    public BugAlreadyExistsException(String message) {
        super(message);
    }
}
