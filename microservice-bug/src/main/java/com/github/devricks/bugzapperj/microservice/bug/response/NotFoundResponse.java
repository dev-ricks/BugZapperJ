package com.github.devricks.bugzapperj.microservice.bug.response;

import org.springframework.http.HttpStatus;

public class NotFoundResponse extends BaseResponse {

    public NotFoundResponse(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public NotFoundResponse(String message, int id) {
        super(message, id, HttpStatus.NOT_FOUND);
    }
}
