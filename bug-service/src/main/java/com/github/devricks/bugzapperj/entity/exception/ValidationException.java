package com.github.devricks.bugzapperj.entity.exception;

import java.util.LinkedHashMap;
import java.util.Map;

public class ValidationException extends RuntimeException {
    private final Map<String, String> errorMessages = new LinkedHashMap<>();

    public ValidationException() {
        super("Entity validation failed");
    }

    public ValidationException(String propertyKey, String message) {
        super("Entity validation failed");
        errorMessages.put(propertyKey, message);
    }

    public ValidationException(Map<String, String> errorMessages) {
        super("Entity validation failed");
        this.errorMessages.putAll(errorMessages);
    }

    @Override
    public String getMessage() {
        return errorMessages.toString();
    }

    public void addErrorMessage(String propertyKey, String message) {
        if (message == null) {
            errorMessages.remove(propertyKey);
            errorMessages.put(propertyKey, null);
        } else {
            errorMessages.merge(propertyKey, message, (existingMessage, newMessage) -> existingMessage + ", " + newMessage);
        }
    }

    public Map<String, String> getValidationErrors() {
        return errorMessages;
    }
}
