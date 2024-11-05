package com.github.devricks.bugzapperj.service.exception;

public class BugNotFoundException extends RuntimeException {
    public BugNotFoundException(String message) {
        super(message);
    }
}
