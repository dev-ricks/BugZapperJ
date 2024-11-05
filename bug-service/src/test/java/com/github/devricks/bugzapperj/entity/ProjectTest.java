package com.github.devricks.bugzapperj.entity;

import com.github.devricks.bugzapperj.entity.exception.ValidationException;
import com.github.devricks.bugzapperj.entity.util.DefaultIDGeneratorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    @BeforeEach
    void setup() {
        Project.resetIDGenerator();
    }

    @Test
    void Project_BuilderWithNullName_ShouldHandleGracefully() {
        // Act
        Project project = new Project.Builder().withName(null).build();
        // Assert
        assertNotNull(project);
        assertNull(project.getName());
    }


    @Test
    void Project_WhenProjectNameIsProvided_ShouldCreateProjectWithAppropriateValues() {
        // Arrange
        String expectedName = "project";
        // Act
        Project project = new Project.Builder().withName(expectedName).build();
        // Assert
        assertNotNull(project);
        assertEquals(expectedName, project.getName());
    }

    @Test
    void Project_BuilderWithBugs_ShouldSetBugs() {
        // Arrange
        Set<Bug> expectedBugs = Set.of(new Bug.Builder().withName("bug1").build());
        // Act
        Project project = new Project.Builder().withName("project").withBugs(expectedBugs).build();
        // Assert
        assertEquals(expectedBugs, project.getBugs());
    }

    @Test
    void Project_BuilderWithNullBugs_ShouldHandleGracefully() {
        // Act
        Project project = new Project.Builder().withName("project").withBugs(null).build();
        // Assert
        assertNotNull(project.getBugs());
    }

    @Test
    void getId_CreateFirstProject_ProjectIdShouldBeOne() {
        // Arrange
        int expectedID = 1;
        Project projectComponentToTest = new Project.Builder()
                .withName("project")
                .build();
        // Act
        projectComponentToTest.createId();
        // Assert
        assertEquals(expectedID, projectComponentToTest.getId());
    }

    @Test
    void getName_CreateProjectWithName_ProjectShouldHaveSameName() {
        // Arrange
        String expectedName = "project";
        Project project = new Project.Builder().withName(expectedName).build();
        // Act
        String actualName = project.getName();
        // Assert
        assertEquals(expectedName, actualName);
    }

    @Test
    void getBugs_CreateProjectThenAddsBug_ProjectShouldHaveAllPropertiesSet() {
        // Arrange
        Project project = new Project.Builder().withName("project").build();
        Bug expectedBug = new Bug.Builder()
                .withName("name")
                .withDescription("description")
                .withProjectId(project.getId())
                .withProjectName(project.getName())
                .build();
        project.addBug(expectedBug);
        // Act
        Set<Bug> actualBugs = project.getBugs();
        // Assert
        assertNotNull(actualBugs);
        assertTrue(actualBugs.contains(expectedBug));
    }

    @Test
    void addBugs_CreateProjectThenAddsMultipleBugs_ProjectShouldHaveAllPropertiesSet() {
        // Arrange
        Project project = new Project.Builder().withName("project").build();
        Bug expectedBug1 = new Bug.Builder()
                .withName("name1")
                .withDescription("description1")
                .withProjectId(project.getId())
                .withProjectName(project.getName())
                .build();
        Bug expectedBug2 = new Bug.Builder()
                .withName("name2")
                .withDescription("description2")
                .withProjectId(project.getId())
                .withProjectName(project.getName())
                .build();
        Bug expectedBug3 = new Bug.Builder()
                .withName("name3")
                .withDescription("description3")
                .withProjectId(project.getId())
                .withProjectName(project.getName())
                .build();
        project.addBugs(Set.of(expectedBug1, expectedBug2, expectedBug3));
        // Act
        Set<Bug> actualBugs = project.getBugs();
        // Assert
        assertNotNull(actualBugs);
        assertTrue(actualBugs.contains(expectedBug1));
        assertTrue(actualBugs.contains(expectedBug2));
        assertTrue(actualBugs.contains(expectedBug3));
    }

    @Test
    void addBugs_WithNull_ShouldHandleGracefully() {
        // Arrange
        Project project = new Project.Builder()
                .withName("project")
                .build();
        // Act
        project.addBugs(null);
        // Assert
        assertNotNull(project.getBugs());
    }

    @Test
    void addBugs_WithDuplicates_ShouldNotAddDuplicates() {
        // Arrange
        Project project = new Project.Builder()
                .withName("project")
                .build();
        Bug bug = new Bug.Builder().withName("bug1").build();
        project.addBug(bug);
        // Act
        project.addBugs(Set.of(bug));
        // Assert
        assertEquals(1, project.getBugs().size());
    }

    @Test
    void resetIDGenerator_IDStartsAtHundredThousand_IDGeneratorShouldBeSetAtZeroAgain() {
        // Arrange
        Project.IDGenerator = new DefaultIDGeneratorFactory().createIDGenerator(100000);
        int expectedID = 0;
        // Act
        Project.resetIDGenerator();
        // Assert
        assertEquals(expectedID, Project.IDGenerator.getCurrentID());
    }

    @Test
    void validate_CreateProjectWithNameNull_ThrowsValidationException() {
        // Arrange
        int expectedNumberOfErrors = 1;
        String expectedName = null;
        Project expectedProject = new Project.Builder().withName(expectedName).build();
        // Act
        // Assert
        ValidationException exception = assertThrows(ValidationException.class, expectedProject::validate);
        assertEquals(expectedNumberOfErrors, exception.getValidationErrors().size());
    }

    @Test
    void validate_CreateProjectWithNameEmpty_ThrowsValidationException() {
        // Arrange
        int expectedNumberOfErrors = 1;
        String expectedName = "";
        Project expectedProject = new Project.Builder().withName(expectedName).build();
        // Act
        // Assert
        ValidationException exception = assertThrows(ValidationException.class, expectedProject::validate);
        assertEquals(expectedNumberOfErrors, exception.getValidationErrors().size());
    }

    @Test
    void validate_CreateProjectWithNameOnlySpaces_ThrowsValidationException() {
        // Arrange
        int expectedNumberOfErrors = 1;
        String expectedName = "   ";
        Project expectedProject = new Project.Builder().withName(expectedName).build();
        // Act
        // Assert
        ValidationException exception = assertThrows(ValidationException.class, expectedProject::validate);
        assertEquals(expectedNumberOfErrors, exception.getValidationErrors().size());

    }

    @Test
    void validate_WithValidName_ShouldNotThrowException() {
        // Arrange
        Project project = new Project.Builder()
                .withName("validName")
                .build();
        // Act & Assert
        assertDoesNotThrow(project::validate);
    }

    @Test
    void createId_CreateProject_ProjectIdShouldBeOne() {
        // Arrange
        int expectedID = 1;
        Project projectComponentToTest = new Project.Builder()
                .withName("project")
                .build();
        // Act
        projectComponentToTest.createId();
        // Assert
        assertEquals(expectedID, projectComponentToTest.getId());
    }

    @Test
    void createId_CreateMultipleProjects_ProjectIdsShouldBeIncremented() {
        // Arrange
        int expectedID1 = 1;
        int expectedID2 = 2;
        Project projectComponentToTest1 = new Project.Builder()
                .withName("project1")
                .build();
        Project projectComponentToTest2 = new Project.Builder()
                .withName("project2")
                .build();
        // Act
        projectComponentToTest1.createId();
        projectComponentToTest2.createId();
        // Assert
        assertEquals(expectedID1, projectComponentToTest1.getId());
        assertEquals(expectedID2, projectComponentToTest2.getId());
    }

    @Test
    void createId_CreateProjectWithIDGeneratorSetTo100000_ProjectIdShouldBe100001() {
        // Arrange
        int expectedID = 100001;
        Project.IDGenerator = new DefaultIDGeneratorFactory().createIDGenerator(100000);
        Project projectComponentToTest = new Project.Builder()
                .withName("project")
                .build();
        // Act
        projectComponentToTest.createId();
        // Assert
        assertEquals(expectedID, projectComponentToTest.getId());
    }

    @Test
    void createId_CreateMultipleProjectsWithIDGeneratorSetTo100000_ProjectIdsShouldBeIncremented() {
        // Arrange
        int expectedID1 = 100001;
        int expectedID2 = 100002;
        Project.IDGenerator = new DefaultIDGeneratorFactory().createIDGenerator(100000);
        Project projectComponentToTest1 = new Project.Builder()
                .withName("project1")
                .build();
        Project projectComponentToTest2 = new Project.Builder()
                .withName("project2")
                .build();
        // Act
        projectComponentToTest1.createId();
        projectComponentToTest2.createId();
        // Assert
        assertEquals(expectedID1, projectComponentToTest1.getId());
        assertEquals(expectedID2, projectComponentToTest2.getId());
    }

    @Test
    void createId_CreateProjectWithIDGeneratorSetToZeroThenResetIDGenerator_ProjectIdShouldBeOne() {
        // Arrange
        int expectedID = 1;
        Project.IDGenerator = new DefaultIDGeneratorFactory().createIDGenerator(0);
        Project projectComponentToTest = new Project.Builder()
                .withName("project")
                .build();
        // Act
        projectComponentToTest.createId();
        // Assert
        assertEquals(expectedID, projectComponentToTest.getId());
    }

    @Test
    void createId_CreateMultipleProjectsWithIDGeneratorSetToZeroThenResetIDGenerator_ProjectIdsShouldBeIncremented() {
        // Arrange
        int expectedID1 = 1;
        int expectedID2 = 2;
        Project.IDGenerator = new DefaultIDGeneratorFactory().createIDGenerator(0);
        Project projectComponentToTest1 = new Project.Builder()
                .withName("project1")
                .build();
        Project projectComponentToTest2 = new Project.Builder()
                .withName("project2")
                .build();
        // Act
        projectComponentToTest1.createId();
        projectComponentToTest2.createId();
        // Assert
        assertEquals(expectedID1, projectComponentToTest1.getId());
        assertEquals(expectedID2, projectComponentToTest2.getId());
    }

    @Test
    void createId_CreateProjectWithIDGeneratorSetTo100000ThenResetIDGenerator_ProjectIdShouldBeOne() {
        // Arrange
        int expectedID = 1;
        Project.IDGenerator = new DefaultIDGeneratorFactory().createIDGenerator(100000);
        Project.resetIDGenerator();
        Project projectComponentToTest = new Project.Builder()
                .withName("project")
                .build();
        // Act
        projectComponentToTest.createId();
        // Assert
        assertEquals(expectedID, projectComponentToTest.getId());
    }

    @Test
    void createId_CreateMultipleProjectsWithIDGeneratorSetTo100000ThenResetIDGenerator_ProjectIdsShouldBeIncremented() {
        // Arrange
        int expectedID1 = 1;
        int expectedID2 = 2;
        Project.IDGenerator = new DefaultIDGeneratorFactory().createIDGenerator(100000);
        Project.resetIDGenerator();
        Project projectComponentToTest1 = new Project.Builder()
                .withName("project1")
                .build();
        Project projectComponentToTest2 = new Project.Builder()
                .withName("project2")
                .build();
        // Act
        projectComponentToTest1.createId();
        projectComponentToTest2.createId();
        // Assert
        assertEquals(expectedID1, projectComponentToTest1.getId());
        assertEquals(expectedID2, projectComponentToTest2.getId());
    }

    @Test
    void createId_CreateProjectWithIDGeneratorSetToZeroThenResetIDGeneratorThenCreateProject_ProjectIdShouldBeOne() {
        // Arrange
        int expectedID = 1;
        Project.IDGenerator = new DefaultIDGeneratorFactory().createIDGenerator(0);
        Project.resetIDGenerator();
        Project projectComponentToTest = new Project.Builder()
                .withName("project")
                .build();
        // Act
        projectComponentToTest.createId();
        // Assert
        assertEquals(expectedID, projectComponentToTest.getId());
    }

    @Test
    void createId_CreateProjectWithIDGeneratorSetToZeroThenResetIDGeneratorThenCreateMultipleProjects_ProjectIdsShouldBeIncremented() {
        // Arrange
        int expectedID1 = 1;
        int expectedID2 = 2;
        Project.IDGenerator = new DefaultIDGeneratorFactory().createIDGenerator(0);
        Project.resetIDGenerator();
        Project projectComponentToTest1 = new Project.Builder()
                .withName("project1")
                .build();
        Project projectComponentToTest2 = new Project.Builder()
                .withName("project2")
                .build();
        // Act
        projectComponentToTest1.createId();
        projectComponentToTest2.createId();
        // Assert
        assertEquals(expectedID1, projectComponentToTest1.getId());
        assertEquals(expectedID2, projectComponentToTest2.getId());
    }

    @Test
    void createId_CreateProjectWithIDGeneratorSetTo100000ThenResetIDGeneratorThenCreateProject_ProjectIdShouldBeOne() {
        // Arrange
        int expectedID = 1;
        Project.IDGenerator = new DefaultIDGeneratorFactory().createIDGenerator(100000);
        Project.resetIDGenerator();
        Project projectComponentToTest = new Project.Builder()
                .withName("project")
                .build();
        // Act
        projectComponentToTest.createId();
        // Assert
        assertEquals(expectedID, projectComponentToTest.getId());
    }

    // new complete the coverage of tests
    @Test
    void createId_CreateProjectWithIDGeneratorSetTo100000ThenResetIDGeneratorThenCreateMultipleProjects_ProjectIdsShouldBeIncremented() {
        // Arrange
        int expectedID1 = 1;
        int expectedID2 = 2;
        Project.IDGenerator = new DefaultIDGeneratorFactory().createIDGenerator(100000);
        Project.resetIDGenerator();
        Project projectComponentToTest1 = new Project.Builder()
                .withName("project1")
                .build();
        Project projectComponentToTest2 = new Project.Builder()
                .withName("project2")
                .build();
        // Act
        projectComponentToTest1.createId();
        projectComponentToTest2.createId();
        // Assert
        assertEquals(expectedID1, projectComponentToTest1.getId());
        assertEquals(expectedID2, projectComponentToTest2.getId());
    }

}