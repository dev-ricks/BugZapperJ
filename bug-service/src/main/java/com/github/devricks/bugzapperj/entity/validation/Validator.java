package com.github.devricks.bugzapperj.entity.validation;

import com.github.devricks.bugzapperj.entity.exception.ValidationException;

public interface Validator {
    void validate() throws ValidationException;
}
