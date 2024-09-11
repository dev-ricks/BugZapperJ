package com.github.devricks.bugzapperj.microservice.bug.response;

import org.springframework.http.HttpStatus;

public class BadRequestResponse extends BaseResponse {

    public BadRequestResponse(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
