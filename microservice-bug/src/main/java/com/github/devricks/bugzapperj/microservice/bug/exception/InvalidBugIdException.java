package com.github.devricks.bugzapperj.microservice.bug.exception;

public class InvalidBugIdException extends RuntimeException {
    public InvalidBugIdException(String message) {
        super(message);
    }
}
