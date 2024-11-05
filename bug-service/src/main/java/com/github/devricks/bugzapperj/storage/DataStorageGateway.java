package com.github.devricks.bugzapperj.storage;

public interface DataStorageGateway<T> {

    boolean save(T object);

    T find(Integer id);
}
