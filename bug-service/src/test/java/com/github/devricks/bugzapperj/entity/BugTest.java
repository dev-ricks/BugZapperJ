package com.github.devricks.bugzapperj.entity;

import com.github.devricks.bugzapperj.entity.exception.ValidationException;
import com.github.devricks.bugzapperj.entity.util.DefaultIDGeneratorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
                .withName("project")
                .build();
        // Act
        Bug componentToTest = new Bug.Builder()
                .withName(expectedName)
                .withDescription(expectedDescription)
                .withProject(project)
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
        String expectedName = "bug";
        // Act
        Bug componentToTest = new Bug.Builder()
                .withName(expectedName)
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
                .withDescription(expectedDescription)
                .build();
        // Assert
        assertNotNull(componentToTest);
        assertEquals(expectedDescription, componentToTest.getDescription());
    }

    @Test
    void BugCreate_WhenProjectPropertyIsSet_ShouldCreateBugWithAppropriateValues() {
        // Arrange
        Project project = new Project.Builder()
                .withName("project")
                .build();
        // Act
        Bug componentToTest = new Bug.Builder()
                .withProject(project)
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
                .withName("bug")
                .withDescription("description")
                .withProject(new Project.Builder()
                        .withName("project")
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
                .withName(expectedName)
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
                .withDescription(expectedDescription)
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
                .withName("project")
                .build();
        Bug bugComponentToTest = new Bug.Builder()
                .withProject(expectedProject)
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
                .withName(bugName)
                .withDescription("description")
                .withProject(new Project.Builder()
                        .withName("project")
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
                .withName(bugName)
                .withDescription("description")
                .withProject(new Project.Builder()
                        .withName("project")
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
                .withName("bug")
                .withDescription(bugDescription)
                .withProject(new Project.Builder()
                        .withName("project")
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
                .withName("bug")
                .withDescription(bugDescription)
                .withProject(new Project.Builder()
                        .withName("project")
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
                .withName("bug")
                .withDescription("description")
                .withProject(bugProject)
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
                .withName(bugName)
                .withDescription(bugDescription)
                .withProject(bugProject)
                .build();
        // Act // Assert
        ValidationException exception = assertThrows(ValidationException.class, bugComponentToTest::validate);
        assertEquals(expectedErrorMessages, exception.getValidationErrors().size());
    }

    @Test
    void resetIDGenerator_IDStartsAtHundredThousand_IDGeneratorShouldBeSetAtZeroAgain() {
        // Arrange
        Bug.IDGenerator = new DefaultIDGeneratorFactory().createIDGenerator(100000);
        int expectedID = 0;
        // Act
        Bug.resetIDGenerator();
        // Assert
        assertEquals(expectedID, Bug.IDGenerator.getCurrentID());
    }

    @Test
    void createId_Bug_ShouldResultInIDOfOne() {
        // Arrange
        int expectedID = 1;
        Bug bugComponentToTest = new Bug.Builder()
                .withName("bug")
                .withDescription("description")
                .withProject(new Project.Builder()
                        .withName("project")
                        .build())
                .build();
        // Act
        bugComponentToTest.createId();
        // Assert
        assertEquals(expectedID, bugComponentToTest.getId());
    }
}