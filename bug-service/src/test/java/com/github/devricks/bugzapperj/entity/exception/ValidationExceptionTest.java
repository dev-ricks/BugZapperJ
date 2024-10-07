package com.github.devricks.bugzapperj.entity.exception;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ValidationExceptionTest {

    @Test
    void addErrorMessage_PropertyBasedErrorMessage_ExceptionShouldContainTheCorrectMessage() {
        // Arrange
        String expectedMessage = "Name is required";
        ValidationException componentToTest = new ValidationException();
        // Act
        componentToTest.addErrorMessage("name", "Name is required");
        // Assert
        assertEquals(expectedMessage, componentToTest.getValidationErrors().get("name"));
    }

    @Test
    void addErrorMessage_MultiplePropertyBasedErrorMessages_ExceptionShouldContainAllMessages() {
        // Arrange
        String expectedNameMessage = "Name is required";
        String expectedDescriptionMessage = "Description is required";
        ValidationException componentToTest = new ValidationException();
        // Act
        componentToTest.addErrorMessage("name", "Name is required");
        componentToTest.addErrorMessage("description", "Description is required");
        // Assert
        assertEquals(expectedNameMessage, componentToTest.getValidationErrors().get("name"));
        assertEquals(expectedDescriptionMessage, componentToTest.getValidationErrors().get("description"));
    }

    @Test
    void addErrorMessage_MultiplePropertyBasedErrorMessagesWithSameProperty_ExceptionShouldContainCommaSeparatedOfAllMessages() {
        // Arrange
        String expectedMessage = "Description is required, Description is missing";
        ValidationException componentToTest = new ValidationException();
        // Act
        componentToTest.addErrorMessage("description", "Description is required");
        componentToTest.addErrorMessage("description", "Description is missing");
        // Assert
        assertEquals(expectedMessage, componentToTest.getValidationErrors().get("description"));
    }

    @Test
    void addErrorMessage_NullProperty_ExceptionShouldContainTheCorrectMessage() {
        // Arrange
        String expectedMessage = "Name is required";
        ValidationException componentToTest = new ValidationException();
        // Act
        componentToTest.addErrorMessage(null, "Name is required");
        // Assert
        assertEquals(expectedMessage, componentToTest.getValidationErrors().get(null));
    }

    @Test
    void addErrorMessage_EmptyProperty_ExceptionShouldContainTheCorrectMessage() {
        // Arrange
        String expectedMessage = "Name is required";
        ValidationException componentToTest = new ValidationException();
        // Act
        componentToTest.addErrorMessage("", "Name is required");
        // Assert
        assertEquals(expectedMessage, componentToTest.getValidationErrors().get(""));
    }

    @Test
    void addErrorMessage_EmptyMessage_ExceptionShouldContainTheCorrectMessage() {
        // Arrange
        String expectedMessage = "";
        ValidationException componentToTest = new ValidationException();
        // Act
        componentToTest.addErrorMessage("name", "");
        // Assert
        assertEquals(expectedMessage, componentToTest.getValidationErrors().get("name"));
    }

    @Test
    void addErrorMessage_NullMessage_ExceptionShouldContainTheCorrectMessage() {
        // Arrange
        String expectedMessage = null;
        ValidationException componentToTest = new ValidationException();
        // Act
        componentToTest.addErrorMessage("name", null);
        // Assert
        assertEquals(expectedMessage, componentToTest.getValidationErrors().get("name"));
    }

    @Test
    void addErrorMessage_NullMessage_ExceptionShouldClearExistingPropertyThenContainTheCorrectMessage() {
        // Arrange
        String expectedMessage = null;
        ValidationException componentToTest = new ValidationException();
        // Act
        componentToTest.addErrorMessage("name", "Name is required");
        componentToTest.addErrorMessage("name", null);
        // Assert
        assertEquals(expectedMessage, componentToTest.getValidationErrors().get("name"));
    }

    @Test
    void addErrorMessage_NullPropertyAndMessage_ExceptionShouldContainTheCorrectMessage() {
        // Arrange
        String expectedMessage = null;
        ValidationException componentToTest = new ValidationException();
        // Act
        componentToTest.addErrorMessage(null, null);
        // Assert
        assertEquals(expectedMessage, componentToTest.getValidationErrors().get(null));
    }

    @Test
    void getValidationErrors_ErrorCreated_ShouldContainCreatedError() {
        // Arrange
        int expectedErrorCount = 1;
        String expectedKey = "name";
        String expectedMessage = "Name is required";
        ValidationException componentToTest = new ValidationException();
        // Act
        componentToTest.addErrorMessage("name", "Name is required");
        // Assert
        assertEquals(expectedErrorCount, componentToTest.getValidationErrors().size());
        assertTrue(componentToTest.getValidationErrors().containsKey(expectedKey));
        assertTrue(componentToTest.getValidationErrors().containsValue(expectedMessage));
    }

    @Test
    void getValidationErrors_MultipleErrorsCreated_ShouldContainAllCreatedErrors() {
        // Arrange
        int expectedErrorCount = 2;
        String expectedNameKey = "name";
        String expectedNameMessage = "Name is required";
        String expectedDescriptionKey = "description";
        String expectedDescriptionMessage = "Description is required";
        ValidationException componentToTest = new ValidationException();
        // Act
        componentToTest.addErrorMessage("name", "Name is required");
        componentToTest.addErrorMessage("description", "Description is required");
        // Assert
        assertEquals(expectedErrorCount, componentToTest.getValidationErrors().size());
        assertTrue(componentToTest.getValidationErrors().containsKey(expectedNameKey));
        assertTrue(componentToTest.getValidationErrors().containsValue(expectedNameMessage));
        assertTrue(componentToTest.getValidationErrors().containsKey(expectedDescriptionKey));
        assertTrue(componentToTest.getValidationErrors().containsValue(expectedDescriptionMessage));
    }

    @Test
    void getValidationErrors_MultipleErrorsCreatedWithSameProperty_ShouldContainCommaSeparatedOfAllCreatedErrors() {
        // Arrange
        int expectedErrorCount = 1;
        String expectedKey = "description";
        String expectedMessage = "Description is required, Description is missing";
        ValidationException componentToTest = new ValidationException();
        // Act
        componentToTest.addErrorMessage("description", "Description is required");
        componentToTest.addErrorMessage("description", "Description is missing");
        // Assert
        assertEquals(expectedErrorCount, componentToTest.getValidationErrors().size());
        assertTrue(componentToTest.getValidationErrors().containsKey(expectedKey));
        assertTrue(componentToTest.getValidationErrors().containsValue(expectedMessage));
    }

    @Test
    void addErrorMessage_CheckThatMessagesCollectionHoldsOrderOfInsertion_ShouldContainMessagesInOrderOfInsertion() {
        // Arrange
        // need to add 20 messages to ensure that the order of insertion is maintained
        String[] expectedMessages = new String[20];
        ValidationException componentToTest = new ValidationException();
        // Act
        for (int i = 0; i < 20; i++) {
            expectedMessages[i] = "Message " + i;
            componentToTest.addErrorMessage("property" + i, expectedMessages[i]);
        }
        // Assert
        int i = 0;
        for (String key : componentToTest.getValidationErrors().keySet()) {
            assertEquals("property" + i, key);
            assertEquals(expectedMessages[i], componentToTest.getValidationErrors().get(key));
            i++;
        }
    }

    @Test
    void EntityValidationException_DefaultConstructor_ShouldInitializeErrorMessages() {
        // Act
        ValidationException exception = new ValidationException();
        // Assert
        assertNotNull(exception.getValidationErrors());
        assertTrue(exception.getValidationErrors().isEmpty());
    }

    @Test
    void EntityValidationException_SingleErrorMessageConstructor_ShouldInitializeErrorMessages() {
        // Arrange
        String propertyKey = "name";
        String message = "Name is required";
        // Act
        ValidationException exception = new ValidationException(propertyKey, message);
        // Assert
        assertEquals(1, exception.getValidationErrors().size());
        assertEquals(message, exception.getValidationErrors().get(propertyKey));
    }

    @Test
    void EntityValidationException_MapConstructor_ShouldInitializeErrorMessages() {
        // Arrange
        Map<String, String> errorMessages = new LinkedHashMap<>();
        errorMessages.put("name", "Name is required");
        errorMessages.put("description", "Description is required");
        // Act
        ValidationException exception = new ValidationException(errorMessages);
        // Assert
        assertEquals(2, exception.getValidationErrors().size());
        assertEquals("Name is required", exception.getValidationErrors().get("name"));
        assertEquals("Description is required", exception.getValidationErrors().get("description"));
    }

    @Test
    void addErrorMessage_ValidErrorMessage_ShouldAddNewErrorMessage() {
        // Arrange
        ValidationException exception = new ValidationException();
        String propertyKey = "name";
        String message = "Name is required";
        // Act
        exception.addErrorMessage(propertyKey, message);
        // Assert
        assertEquals(message, exception.getValidationErrors().get(propertyKey));
    }

    @Test
    void addErrorMessage_DuplicateKey_ShouldAppendToExistingErrorMessage() {
        // Arrange
        ValidationException exception = new ValidationException();
        String propertyKey = "name";
        String message1 = "Name is required";
        String message2 = "Name is missing";
        // Act
        exception.addErrorMessage(propertyKey, message1);
        exception.addErrorMessage(propertyKey, message2);
        // Assert
        assertEquals("Name is required, Name is missing", exception.getValidationErrors().get(propertyKey));
    }

    @Test
    void addErrorMessage_NullMessage_ShouldHandleNullMessage() {
        // Arrange
        ValidationException exception = new ValidationException();
        String propertyKey = "name";
        // Act
        exception.addErrorMessage(propertyKey, null);
        // Assert
        assertNull(exception.getValidationErrors().get(propertyKey));
    }

    @Test
    void getValidationErrors_ValidErrorMessage_ShouldReturnCorrectErrorMessages() {
        // Arrange
        ValidationException exception = new ValidationException();
        String propertyKey = "name";
        String message = "Name is required";
        exception.addErrorMessage(propertyKey, message);
        // Act
        Map<String, String> validationErrors = exception.getValidationErrors();
        // Assert
        assertEquals(1, validationErrors.size());
        assertEquals(message, validationErrors.get(propertyKey));
    }
}