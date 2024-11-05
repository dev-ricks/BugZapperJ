package com.github.devricks.bugzapperj.service;

public interface DataHandlerService {

    Object convert(Object object, Class<?> clazz);

    Object convert(Object object, Exception e, Class<?> clazz);

    Object merge(Object object, Object object2, Class<?> clazz);
}
