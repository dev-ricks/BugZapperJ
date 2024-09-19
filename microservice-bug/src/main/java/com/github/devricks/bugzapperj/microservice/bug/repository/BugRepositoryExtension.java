package com.github.devricks.bugzapperj.microservice.bug.repository;

import com.github.devricks.bugzapperj.microservice.bug.exception.BugAlreadyExistsException;
import com.github.devricks.bugzapperj.microservice.bug.exception.InvalidBugIdException;
import com.github.devricks.bugzapperj.microservice.bug.exception.NotABugEntityException;

public interface BugRepositoryExtension<T> {
    <S extends T> S duplicateNotAllowedToSave(S entity) throws BugAlreadyExistsException, NotABugEntityException, InvalidBugIdException;
}
