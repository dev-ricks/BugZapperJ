package com.github.devricks.bugzapperj.microservice.bug.exception;

public class BugNotFoundException extends RuntimeException {
    public BugNotFoundException(String message) {
        super(message);
    }
}
