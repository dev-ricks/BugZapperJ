package com.github.devricks.bugzapperj.microservice.bug.response;

import org.springframework.http.HttpStatus;

public interface RestResponse {

    String getMessage();

    int getId();

    HttpStatus getStatus();
}
