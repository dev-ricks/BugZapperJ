package com.github.devricks.bugzapperj.entity;

import com.github.devricks.bugzapperj.entity.exception.ValidationException;
import com.github.devricks.bugzapperj.entity.util.IDGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class BugTest {

    @BeforeEach
    void setup() {
        Bug.resetIDGenerator();
    }

    @Test
    void BugCreate_WhenAllPropertiesAreProvided_ShouldCreateBugWithAppropriateValues() {
        // Arrange
        String expectedName = "bug";
        String expectedDescription = "description";
        Project project = new Project.Builder()
                .setName("project")
                .build();
        // Act
        Bug componentToTest = new Bug.Builder()
                .setName(expectedName)
                .setDescription(expectedDescription)
                .setProject(project)
                .build();
        // Assert
        assertNotNull(componentToTest);
        assertEquals(expectedName, componentToTest.getName());
        assertEquals(expectedDescription, componentToTest.getDescription());
        assertEquals(project, componentToTest.getProject());
    }

    @Test
    void BugCreate_WhenNamePropertyIsSet_ShouldCreateBugWithAppropriateValues() {
        // Arrange
        int expectedID = 1;
        String expectedName = "bug";
        // Act
        Bug componentToTest = new Bug.Builder()
                .setName(expectedName)
                .build();
        // Assert
        assertNotNull(componentToTest);
        assertEquals(expectedName, componentToTest.getName());
    }

    @Test
    void BugCreate_WhenDescriptionPropertyIsSet_ShouldCreateBugWithAppropriateValues() {
        // Arrange
        String expectedDescription = "description";
        // Act
        Bug componentToTest = new Bug.Builder()
                .setDescription(expectedDescription)
                .build();
        // Assert
        assertNotNull(componentToTest);
        assertEquals(expectedDescription, componentToTest.getDescription());
    }

    @Test
    void BugCreate_WhenProjectPropertyIsSet_ShouldCreateBugWithAppropriateValues() {
        // Arrange
        Project project = new Project.Builder()
                .setName("project")
                .build();
        // Act
        Bug componentToTest = new Bug.Builder()
                .setProject(project)
                .build();
        // Assert
        assertNotNull(componentToTest);
        assertEquals(project, componentToTest.getProject());
    }

    @Test
    void getId_CreateFirstBug_BugIdShouldBeZero() {
        // Arrange
        int expectedID = 0;
        Bug bugComponentToTest = new Bug.Builder()
                .setName("bug")
                .setDescription("description")
                .setProject(new Project.Builder()
                        .setName("project")
                        .build())
                .build();
        // Act
        int actualID = bugComponentToTest.getId();
        // Assert
        assertEquals(expectedID, actualID);
    }

    @Test
    void getName_CreateBugWithName_BugShouldHaveSameName() {
        // Arrange
        String expectedName = "bug";
        Bug bugComponentToTest = new Bug.Builder()
                .setName(expectedName)
                .build();
        // Act
        String actualName = bugComponentToTest.getName();
        // Assert
        assertEquals(expectedName, actualName);
    }

    @Test
    void getDescription_CreateBugWithDescription_BugShouldHaveSameDescription() {
        // Arrange
        String expectedDescription = "description";
        Bug bugComponentToTest = new Bug.Builder()
                .setDescription(expectedDescription)
                .build();
        // Act
        String actualDescription = bugComponentToTest.getDescription();
        // Assert
        assertEquals(expectedDescription, actualDescription);
    }

    @Test
    void getProject_CreateBugWithProject_BugShouldHaveSameProject() {
        // Arrange
        Project expectedProject = new Project.Builder()
                .setName("project")
                .build();
        Bug bugComponentToTest = new Bug.Builder()
                .setProject(expectedProject)
                .build();
        // Act
        Project actualProject = bugComponentToTest.getProject();
        // Assert
        assertEquals(expectedProject, actualProject);
    }

    @Test
    void validate_BugWithNullName_ThrowsEntityValidationException() {
        // Arrange
        String bugName = null;
        int expectedErrorMessages = 1;
        Bug bugComponentToTest = new Bug.Builder()
                .setName(bugName)
                .setDescription("description")
                .setProject(new Project.Builder()
                        .setName("project")
                        .build())
                .build();
        // Act // Assert
        ValidationException exception = assertThrows(ValidationException.class, bugComponentToTest::validate);
        assertEquals(expectedErrorMessages, exception.getValidationErrors().size());
    }

    @Test
    void validate_BugWithEmptyName_ThrowsEntityValidationException() {
        // Arrange
        String bugName = "";
        int expectedErrorMessages = 1;
        Bug bugComponentToTest = new Bug.Builder()
                .setName(bugName)
                .setDescription("description")
                .setProject(new Project.Builder()
                        .setName("project")
                        .build())
                .build();
        // Act // Assert
        ValidationException exception = assertThrows(ValidationException.class, bugComponentToTest::validate);
        assertEquals(expectedErrorMessages, exception.getValidationErrors().size());
    }

    @Test
    void validate_BugWithNull_ThrowsEntityValidationException() {
        // Arrange
        String bugDescription = null;
        int expectedErrorMessages = 1;
        Bug bugComponentToTest = new Bug.Builder()
                .setName("bug")
                .setDescription(bugDescription)
                .setProject(new Project.Builder()
                        .setName("project")
                        .build())
                .build();
        // Act // Assert
        ValidationException exception = assertThrows(ValidationException.class, bugComponentToTest::validate);
        assertEquals(expectedErrorMessages, exception.getValidationErrors().size());
    }

    @Test
    void validate_BugWithEmptyDescription_ThrowsEntityValidationException() {
        // Arrange
        String bugDescription = "";
        int expectedErrorMessages = 1;
        Bug bugComponentToTest = new Bug.Builder()
                .setName("bug")
                .setDescription(bugDescription)
                .setProject(new Project.Builder()
                        .setName("project")
                        .build())
                .build();
        // Act
        // Assert
        ValidationException exception = assertThrows(ValidationException.class, bugComponentToTest::validate);
        assertEquals(expectedErrorMessages, exception.getValidationErrors().size());
    }

    @Test
    void validate_BugWithNullProject_ThrowsEntityValidationException() {
        // Arrange
        Project bugProject = null;
        int expectedErrorMessages = 1;
        Bug bugComponentToTest = new Bug.Builder()
                .setName("bug")
                .setDescription("description")
                .setProject(bugProject)
                .build();
        // Act // Assert
        ValidationException exception = assertThrows(ValidationException.class, bugComponentToTest::validate);
        assertEquals(expectedErrorMessages, exception.getValidationErrors().size());
    }

    @Test
    void validate_BugWithNoNameAndDescriptionAndProject_ThrowsEntityValidationException() {
        // Arrange
        String bugName = null;
        String bugDescription = null;
        Project bugProject = null;
        int expectedErrorMessages = 3;
        Bug bugComponentToTest = new Bug.Builder()
                .setName(bugName)
                .setDescription(bugDescription)
                .setProject(bugProject)
                .build();
        // Act // Assert
        ValidationException exception = assertThrows(ValidationException.class, bugComponentToTest::validate);
        assertEquals(expectedErrorMessages, exception.getValidationErrors().size());
    }

    @Test
    void resetIDGenerator_IDStartsAtHundredThousand_IDGeneratorShouldBeSetAtZeroAgain() {
        // Arrange
        Bug bugComponentToTest = new Bug.Builder()
                .setName("bug")
                .setDescription("description")
                .setProject(new Project.Builder()
                        .setName("project")
                        .build())
                .build();
        Bug.IDGeneratorBug = new IDGenerator(new AtomicInteger(100000));
        int expectedID = 0;
        // Act
        Bug.resetIDGenerator();
        // Assert
        assertEquals(expectedID, Bug.IDGeneratorBug.getCurrentID());
    }

    @Test
    void createId_Bug_ShouldResultInIDOfOne() {
        // Arrange
        int expectedID = 1;
        Bug bugComponentToTest = new Bug.Builder()
                .setName("bug")
                .setDescription("description")
                .setProject(new Project.Builder()
                        .setName("project")
                        .build())
                .build();
        // Act
        bugComponentToTest.createId();
        // Assert
        assertEquals(expectedID, bugComponentToTest.getId());
    }
}