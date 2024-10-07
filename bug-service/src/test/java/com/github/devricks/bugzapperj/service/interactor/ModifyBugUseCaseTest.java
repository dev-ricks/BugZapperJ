package com.github.devricks.bugzapperj.service.interactor;

import com.github.devricks.bugzapperj.entity.Bug;
import com.github.devricks.bugzapperj.entity.Project;
import com.github.devricks.bugzapperj.entity.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModifyBugUseCaseTest {

    @BeforeEach
    void setUp() {
        Bug.resetIDGenerator();
    }

    @Test
    void createBug_WhenBugDataIsValid_ReturnCreatedBug() {
        // Arrange
        ModifyBugUseCase modifyBugUseCase = new ModifyBugUseCase();
        String expectedName = "name";
        String expectedDescription = "description";
        Project expectedProject = new Project.Builder()
                .setName("project")
                .build();
        // Act
        Bug bug = modifyBugUseCase.createBug(expectedName, expectedDescription, expectedProject);
        // Assert
        assertNotNull(bug);
        assertEquals(expectedName, bug.getName());
        assertEquals(expectedDescription, bug.getDescription());
        assertEquals(expectedProject, bug.getProject());
    }

    @Test
    void createBug_WhenBugDataIsMissingName_ThrowsEntityValidationException() {
        // Arrange
        int expectedErrorCount = 1;
        ModifyBugUseCase componentToTest = new ModifyBugUseCase();
        Bug expectedBug = new Bug.Builder()
                .setDescription("description")
                .setProject(new Project.Builder()
                        .setName("project")
                        .build())
                .build();
        // Act
        // Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> componentToTest.createBug(null, expectedBug.getDescription(), expectedBug.getProject()));
        assertEquals(expectedErrorCount, exception.getValidationErrors().size());
    }

    @Test
    void createBug_WhenBugDataIsMissingDescription_ThrowsEntityValidationException() {
        // Arrange
        int expectedErrorCount = 1;
        ModifyBugUseCase componentToTest = new ModifyBugUseCase();
        Bug expectedBug = new Bug.Builder()
                .setName("name")
                .setProject(new Project.Builder()
                        .setName("project")
                        .build())
                .build();
        // Act
        // Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> componentToTest.createBug(expectedBug.getName(), null, expectedBug.getProject()));
        assertEquals(expectedErrorCount, exception.getValidationErrors().size());
    }

    @Test
    void createBug_WhenBugDataIsMissingProject_ThrowsEntityValidationException() {
        // Arrange
        int expectedErrorCount = 1;
        ModifyBugUseCase componentToTest = new ModifyBugUseCase();
        Bug expectedBug = new Bug.Builder()
                .setName("name")
                .setDescription("description")
                .build();
        // Act
        // Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> componentToTest.createBug(expectedBug.getName(), expectedBug.getDescription(), null));
        assertEquals(expectedErrorCount, exception.getValidationErrors().size());
    }

    @Test
    void createBug_WhenBugDataIsMissingBothNameAndDescription_ThrowsEntityValidationException() {
        // Arrange
        int expectedErrorCount = 2;
        ModifyBugUseCase componentToTest = new ModifyBugUseCase();
        Project expectedProject = new Project.Builder()
                .setName("project")
                .build();
        // Act
        // Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> componentToTest.createBug(null, null, expectedProject));
        assertEquals(expectedErrorCount, exception.getValidationErrors().size());
    }

    @Test
    void createBug_WhenBugDataIsMissingNameAndDescriptionAndProject_ThrowsEntityValidationException() {
        // Arrange
        int expectedErrorCount = 3;
        ModifyBugUseCase componentToTest = new ModifyBugUseCase();
        String expectedName = null;
        String expectedDescription = null;
        Project expectedProject = null;
        // Act
        // Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> componentToTest.createBug(expectedName, expectedDescription, expectedProject));
        assertEquals(expectedErrorCount, exception.getValidationErrors().size());
    }

    @Test
    void createBug_WhenTwoBugsAreCreated_ReturnBugsWithDifferentIdsWithinOneOfEachOther() {
        // Arrange
        ModifyBugUseCase modifyBugUseCase = new ModifyBugUseCase();
        String expectedName = "name";
        String expectedDescription = "description";
        Project expectedProject = new Project.Builder()
                .setName("project")
                .build();
        int expectedIdDifference = 1;
        // Act
        Bug bug1 = modifyBugUseCase.createBug(expectedName, expectedDescription, expectedProject);
        Bug bug2 = modifyBugUseCase.createBug(expectedName, expectedDescription, expectedProject);
        // Assert
        assertNotEquals(bug1.getId(), bug2.getId());
        assertEquals(expectedIdDifference, Math.abs(bug1.getId() - bug2.getId()));
    }

    @Test
    void createBug_WhenBugIsCreated_ReturnBugWithIncrementedId() {
        // Arrange
        ModifyBugUseCase modifyBugUseCase = new ModifyBugUseCase();
        String expectedName = "name";
        String expectedDescription = "description";
        Project expectedProject = new Project.Builder()
                .setName("project")
                .build();
        int expectedId = 1;
        // Act
        Bug bug = modifyBugUseCase.createBug(expectedName, expectedDescription, expectedProject);
        // Assert
        assertEquals(expectedId, bug.getId());
    }

    @Test
    void createBug_WhenBugDataFailsValidation_IncrementIdShouldNotBeGenerated() {
        // Arrange
        ModifyBugUseCase modifyBugUseCase = new ModifyBugUseCase();
        String expectedName = null;
        String expectedDescription = "description";
        Project expectedProject = new Project.Builder()
                .setName("project")
                .build();
        int expectedId = 0;
        // Act // Assert
        assertThrows(ValidationException.class, () -> modifyBugUseCase.createBug(expectedName, expectedDescription, expectedProject));
        assertEquals(expectedId, Bug.IDGeneratorBug.getCurrentID());
    }
}