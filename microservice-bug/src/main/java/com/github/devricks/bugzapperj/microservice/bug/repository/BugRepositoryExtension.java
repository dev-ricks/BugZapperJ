package com.github.devricks.bugzapperj.microservice.bug.repository;

public interface BugRepositoryExtension<T> {
    <S extends T> S duplicateNotAllowedToSave(S entity);
}
