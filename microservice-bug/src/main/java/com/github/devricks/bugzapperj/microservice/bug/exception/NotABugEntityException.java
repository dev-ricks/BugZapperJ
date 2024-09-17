package com.github.devricks.bugzapperj.microservice.bug.exception;

public class NotABugEntityException extends RuntimeException {

    public NotABugEntityException(String message) {
        super(message);
    }
}
