package com.github.devricks.bugzapperj.entity;

import com.github.devricks.bugzapperj.data.InputData;
import com.github.devricks.bugzapperj.entity.exception.ValidationException;
import com.github.devricks.bugzapperj.entity.util.DefaultIDGeneratorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testing.data.InputDataForBugEntities;

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
        Integer expectedProjectId = 1;
        String expectedProjectName = "project";
        // Act
        Bug componentToTest = new Bug.Builder().withName(expectedName).withDescription(expectedDescription).withProjectId(expectedProjectId).withProjectName(expectedProjectName).build();
        // Assert
        assertNotNull(componentToTest);
        assertEquals(expectedName, componentToTest.getName());
        assertEquals(expectedDescription, componentToTest.getDescription());
        assertEquals(expectedProjectId, componentToTest.getProjectId());
        assertEquals(expectedProjectName, componentToTest.getProjectName());
    }

    @Test
    void BugCreate_WhenNamePropertyIsSet_ShouldCreateBugWithAppropriateValues() {
        // Arrange
        String expectedName = "bug";
        // Act
        Bug componentToTest = new Bug.Builder().withName(expectedName).build();
        // Assert
        assertNotNull(componentToTest);
        assertEquals(expectedName, componentToTest.getName());
    }

    @Test
    void BugCreate_WhenDescriptionPropertyIsSet_ShouldCreateBugWithAppropriateValues() {
        // Arrange
        String expectedDescription = "description";
        // Act
        Bug componentToTest = new Bug.Builder().withDescription(expectedDescription).build();
        // Assert
        assertNotNull(componentToTest);
        assertEquals(expectedDescription, componentToTest.getDescription());
    }

    @Test
    void BugCreate_WhenProjectPropertyIsSet_ShouldCreateBugWithAppropriateValues() {
        // Arrange
        Integer expectedProjectId = 1;
        String expectedProjectName = "project";
        // Act
        Bug componentToTest = new Bug.Builder().withProjectId(expectedProjectId).withProjectName(expectedProjectName).build();
        // Assert
        assertNotNull(componentToTest);
        assertEquals(expectedProjectId, componentToTest.getProjectId());
        assertEquals(expectedProjectName, componentToTest.getProjectName());
    }

    @Test
    void getId_CreateFirstBug_BugIdShouldBeZero() {
        // Arrange
        int expectedID = 0;
        Bug bugComponentToTest = new Bug.Builder().withName("bug").withDescription("description").build();
        // Act
        int actualID = bugComponentToTest.getId();
        // Assert
        assertEquals(expectedID, actualID);
    }

    @Test
    void getName_CreateBugWithName_BugShouldHaveSameName() {
        // Arrange
        String expectedName = "bug";
        Bug bugComponentToTest = new Bug.Builder().withName(expectedName).build();
        // Act
        String actualName = bugComponentToTest.getName();
        // Assert
        assertEquals(expectedName, actualName);
    }

    @Test
    void getDescription_CreateBugWithDescription_BugShouldHaveSameDescription() {
        // Arrange
        String expectedDescription = "description";
        Bug bugComponentToTest = new Bug.Builder().withDescription(expectedDescription).build();
        // Act
        String actualDescription = bugComponentToTest.getDescription();
        // Assert
        assertEquals(expectedDescription, actualDescription);
    }

    @Test
    void getProject_CreateBugWithProject_BugShouldHaveSameProject() {
        // Arrange
        Integer expectedProjectId = 1;
        String expectedProjectName = "project";
        Bug bugComponentToTest = new Bug.Builder().withProjectId(expectedProjectId).withProjectName(expectedProjectName).build();
        // Act // Assert
        assertEquals(expectedProjectId, bugComponentToTest.getProjectId());
        assertEquals(expectedProjectName, bugComponentToTest.getProjectName());
    }

    @Test
    void validate_BugWithNullName_ThrowsEntityValidationException() {
        // Arrange
        String bugName = null;
        int expectedErrorMessages = 1;
        Bug bugComponentToTest = new Bug.Builder().withName(bugName).withDescription("description").build();
        // Act // Assert
        ValidationException exception = assertThrows(ValidationException.class, bugComponentToTest::validate);
        assertEquals(expectedErrorMessages, exception.getValidationErrors().size());
    }

    @Test
    void validate_BugWithEmptyName_ThrowsEntityValidationException() {
        // Arrange
        String bugName = "";
        int expectedErrorMessages = 1;
        Bug bugComponentToTest = new Bug.Builder().withName(bugName).withDescription("description").build();
        // Act // Assert
        ValidationException exception = assertThrows(ValidationException.class, bugComponentToTest::validate);
        assertEquals(expectedErrorMessages, exception.getValidationErrors().size());
    }

    @Test
    void validate_BugWithNullDescription_ThrowsEntityValidationException() {
        // Arrange
        String bugDescription = null;
        int expectedErrorMessages = 1;
        Bug bugComponentToTest = new Bug.Builder().withName("bug").withDescription(bugDescription).build();
        // Act // Assert
        ValidationException exception = assertThrows(ValidationException.class, bugComponentToTest::validate);
        assertEquals(expectedErrorMessages, exception.getValidationErrors().size());
    }

    @Test
    void validate_BugWithEmptyDescription_ThrowsEntityValidationException() {
        // Arrange
        String bugDescription = "";
        int expectedErrorMessages = 1;
        Bug bugComponentToTest = new Bug.Builder().withName("bug").withDescription(bugDescription).build();
        // Act
        // Assert
        ValidationException exception = assertThrows(ValidationException.class, bugComponentToTest::validate);
        assertEquals(expectedErrorMessages, exception.getValidationErrors().size());
    }

    @Test
    void validate_BugWithNoNameAndDescription_ThrowsEntityValidationException() {
        // Arrange
        String bugName = null;
        String bugDescription = null;
        int expectedErrorMessages = 2;
        Bug bugComponentToTest = new Bug.Builder().withName(bugName).withDescription(bugDescription).build();
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
        int expectedID = 1;
        Bug bugComponentToTest = new Bug.Builder().withName("bug").withDescription("description").build();
        bugComponentToTest.createId();
        assertEquals(expectedID, bugComponentToTest.getId());
    }

    @Test
    void createBug_GivenBug_ThenReturnBugWithInactivatedFalse() {
        Bug whenBugComponentToTest = new Bug.Builder().withName("bug").withDescription("description").build();
        boolean thenInactivated = whenBugComponentToTest.isInactivated();
        assertFalse(thenInactivated);
    }

    @Test
    void createBug_GivenBug_ThenReturnBugWithPersistedFalse() {
        Bug whenBugComponentToTest = new Bug.Builder().withName("bug").withDescription("description").build();
        boolean thenPersisted = whenBugComponentToTest.isPersisted();
        assertFalse(thenPersisted);
    }

    @Test
    void createBug_GivenBug_ThenReturnBugWithInactivatedTrue() {
        Bug whenBugComponentToTest = new Bug.Builder().withName("bug").withDescription("description").build();
        whenBugComponentToTest.setInactivated(true);
        boolean thenInactivated = whenBugComponentToTest.isInactivated();
        assertTrue(thenInactivated);
    }

    @Test
    void createBug_GivenValidInputData_ThenReturnBugWithPersistedTrue() {
        InputData inputData = InputDataForBugEntities.createValidInputData();
        Bug whenBugComponentToTest = new Bug.Builder().build(inputData);
        whenBugComponentToTest.setPersisted(true);
        boolean thenPersisted = whenBugComponentToTest.isPersisted();
        assertTrue(thenPersisted);
    }

    @Test
    void createBug_GivenValidInputData_ThenReturnBugWithInactivatedTrue() {
        InputData inputData = InputDataForBugEntities.createValidInputData();
        Bug whenBugComponentToTest = new Bug.Builder().build(inputData);
        whenBugComponentToTest.setInactivated(true);
        boolean thenInactivated = whenBugComponentToTest.isInactivated();
        assertTrue(thenInactivated);
    }

    @Test
    void createBug_GivenValidInputData_ThenReturnBugWithInactivatedFalse() {
        InputData inputData = InputDataForBugEntities.createValidInputData();
        Bug whenBugComponentToTest = new Bug.Builder().build(inputData);
        boolean thenInactivated = whenBugComponentToTest.isInactivated();
        assertFalse(thenInactivated);
    }

    @Test
    void createBug_GivenValidInputData_ThenReturnBugWithPersistedFalse() {
        InputData inputData = InputDataForBugEntities.createValidInputData();
        Bug whenBugComponentToTest = new Bug.Builder().build(inputData);
        boolean thenPersisted = whenBugComponentToTest.isPersisted();
        assertFalse(thenPersisted);
    }

    @Test
    void createBug_GivenValidInputData_ThenReturnBugWithAppropriateValues() {
        InputData inputData = InputDataForBugEntities.createValidInputData();
        Bug whenBugComponentToTest = new Bug.Builder().build(inputData);
        assertEquals(inputData.name, whenBugComponentToTest.getName());
        assertEquals(inputData.description, whenBugComponentToTest.getDescription());
        assertEquals(inputData.projectId, whenBugComponentToTest.getProjectId());
        assertEquals(inputData.projectName, whenBugComponentToTest.getProjectName());
    }

    @Test
    void createBug_GivenValidInputData_ThenReturnBugWithAppropriateValuesAndInactivatedTrue() {
        InputData inputData = InputDataForBugEntities.createValidInputData();
        Bug whenBugComponentToTest = new Bug.Builder().build(inputData);
        whenBugComponentToTest.setInactivated(true);
        assertEquals(inputData.name, whenBugComponentToTest.getName());
        assertEquals(inputData.description, whenBugComponentToTest.getDescription());
        assertEquals(inputData.projectId, whenBugComponentToTest.getProjectId());
        assertEquals(inputData.projectName, whenBugComponentToTest.getProjectName());
    }

    @Test
    void createBug_GivenValidInputData_ThenReturnBugWithAppropriateValuesAndPersistedTrue() {
        InputData inputData = InputDataForBugEntities.createValidInputData();
        Bug whenBugComponentToTest = new Bug.Builder().build(inputData);
        whenBugComponentToTest.setPersisted(true);
        assertEquals(inputData.name, whenBugComponentToTest.getName());
        assertEquals(inputData.description, whenBugComponentToTest.getDescription());
        assertEquals(inputData.projectId, whenBugComponentToTest.getProjectId());
        assertEquals(inputData.projectName, whenBugComponentToTest.getProjectName());
    }

    @Test
    void createBug_GivenValidInputData_ThenReturnBugWithAppropriateValuesAndPersistedTrueAndInactivatedTrue() {
        InputData inputData = InputDataForBugEntities.createValidInputData();
        Bug whenBugComponentToTest = new Bug.Builder().build(inputData);
        whenBugComponentToTest.setPersisted(true);
        whenBugComponentToTest.setInactivated(true);
        assertEquals(inputData.name, whenBugComponentToTest.getName());
        assertEquals(inputData.description, whenBugComponentToTest.getDescription());
        assertEquals(inputData.projectId, whenBugComponentToTest.getProjectId());
        assertEquals(inputData.projectName, whenBugComponentToTest.getProjectName());
    }

    @Test
    void createBug_GivenValidInputData_ThenReturnBugWithAppropriateValuesAndPersistedFalseAndInactivatedFalse() {
        InputData inputData = InputDataForBugEntities.createValidInputData();
        Bug whenBugComponentToTest = new Bug.Builder().build(inputData);
        whenBugComponentToTest.setPersisted(false);
        whenBugComponentToTest.setInactivated(false);
        assertEquals(inputData.name, whenBugComponentToTest.getName());
        assertEquals(inputData.description, whenBugComponentToTest.getDescription());
        assertEquals(inputData.projectId, whenBugComponentToTest.getProjectId());
        assertEquals(inputData.projectName, whenBugComponentToTest.getProjectName());
    }

    @Test
    void createBug_GivenValidInputData_ThenReturnBugWithAppropriateValuesAndPersistedFalseAndInactivatedTrue() {
        InputData inputData = InputDataForBugEntities.createValidInputData();
        Bug whenBugComponentToTest = new Bug.Builder().build(inputData);
        whenBugComponentToTest.setPersisted(false);
        whenBugComponentToTest.setInactivated(true);
        assertEquals(inputData.name, whenBugComponentToTest.getName());
        assertEquals(inputData.description, whenBugComponentToTest.getDescription());
        assertEquals(inputData.projectId, whenBugComponentToTest.getProjectId());
        assertEquals(inputData.projectName, whenBugComponentToTest.getProjectName());
    }

    @Test
    void createBug_GivenValidInputData_ThenReturnBugWithAppropriateValuesAndPersistedTrueAndInactivatedFalse() {
        InputData inputData = InputDataForBugEntities.createValidInputData();
        Bug whenBugComponentToTest = new Bug.Builder().build(inputData);
        whenBugComponentToTest.setPersisted(true);
        whenBugComponentToTest.setInactivated(false);
        assertEquals(inputData.name, whenBugComponentToTest.getName());
        assertEquals(inputData.description, whenBugComponentToTest.getDescription());
        assertEquals(inputData.projectId, whenBugComponentToTest.getProjectId());
        assertEquals(inputData.projectName, whenBugComponentToTest.getProjectName());
    }

    @Test
    void createBug_GivenInvalidInputDataEmptyName_ThenReturnBugWithAppropriateValues() {
        InputData inputData = InputDataForBugEntities.createInputDataWithEmptyName();
        Bug whenBugComponentToTest = new Bug.Builder().build(inputData);
        assertEquals(inputData.name, whenBugComponentToTest.getName());
        assertEquals(inputData.description, whenBugComponentToTest.getDescription());
        assertEquals(inputData.projectId, whenBugComponentToTest.getProjectId());
        assertEquals(inputData.projectName, whenBugComponentToTest.getProjectName());
    }

    @Test
    void createBug_GivenInvalidInputDataNullName_ThenReturnBugWithAppropriateValuesAndInactivatedTrue() {
        InputData inputData = InputDataForBugEntities.createInputDataWithNullName();
        Bug whenBugComponentToTest = new Bug.Builder().build(inputData);
        whenBugComponentToTest.setInactivated(true);
        assertEquals(inputData.name, whenBugComponentToTest.getName());
        assertEquals(inputData.description, whenBugComponentToTest.getDescription());
        assertEquals(inputData.projectId, whenBugComponentToTest.getProjectId());
        assertEquals(inputData.projectName, whenBugComponentToTest.getProjectName());
    }

}