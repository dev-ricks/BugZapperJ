package com.github.devricks.bugzapperj.service.converter;

public interface ObjectConversionService {

    Object convert(Object object, Class<?> clazz);

    Object convert(Object object, Exception e, Class<?> clazz);
}
