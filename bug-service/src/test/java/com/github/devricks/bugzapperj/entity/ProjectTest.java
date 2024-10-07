package com.github.devricks.bugzapperj.entity;

import com.github.devricks.bugzapperj.entity.exception.ValidationException;
import com.github.devricks.bugzapperj.entity.util.IDGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    @BeforeEach
    void setup() {
        Project.resetIDGenerator();
    }

    @Test
    void Project_WhenProjectNameIsProvided_ShouldCreateProjectWithAppropriateValues() {
        // Arrange
        int expectedID = 1;
        String expectedName = "project";
        // Act
        Project project = new Project.Builder().setName(expectedName).build();
        // Assert
        assertNotNull(project);
        assertEquals(expectedID, project.getId());
        assertEquals(expectedName, project.getName());
    }

    @Test
    void Project_BuilderWithBugs_ShouldSetBugs() {
        // Arrange
        Set<Bug> expectedBugs = Set.of(new Bug.Builder().setName("bug1").build());
        // Act
        Project project = new Project.Builder().setName("project").setBugs(expectedBugs).build();
        // Assert
        assertEquals(expectedBugs, project.getBugs());
    }

    @Test
    void Project_BuilderWithNullBugs_ShouldHandleGracefully() {
        // Act
        Project project = new Project.Builder().setName("project").setBugs(null).build();
        // Assert
        assertNotNull(project.getBugs());
    }

    @Test
    void getId_CreateFirstProject_ProjectIdShouldBeOne() {
        // Arrange
        int expectedID = 1;
        Project project = new Project.Builder().setName("project").build();
        // Act
        int actualID = project.getId();
        // Assert
        assertEquals(expectedID, actualID);
    }

    @Test
    void getName_CreateProjectWithName_ProjectShouldHaveSameName() {
        // Arrange
        String expectedName = "project";
        Project project = new Project.Builder().setName(expectedName).build();
        // Act
        String actualName = project.getName();
        // Assert
        assertEquals(expectedName, actualName);
    }

    @Test
    void getBugs_CreateProjectThenAddsBug_ProjectShouldHaveAllPropertiesSet() {
        // Arrange
        Project project = new Project.Builder().setName("project").build();
        Bug expectedBug = new Bug.Builder().setName("name").setDescription("description").setProject(project).build();
        project.addBug(expectedBug);
        // Act
        Set<Bug> actualBugs = project.getBugs();
        Set<Bug> bugProjectBugs = expectedBug.getProject().getBugs();
        // Assert
        assertNotNull(actualBugs);
        assertNotNull(bugProjectBugs);
        assertTrue(actualBugs.contains(expectedBug));
        assertTrue(bugProjectBugs.contains(expectedBug));
    }

    @Test
    void addBugs_CreateProjectThenAddsMultipleBugs_ProjectShouldHaveAllPropertiesSet() {
        // Arrange
        Project project = new Project.Builder().setName("project").build();
        Bug expectedBug1 = new Bug.Builder().setName("name1").setDescription("description1").setProject(project).build();
        Bug expectedBug2 = new Bug.Builder().setName("name2").setDescription("description2").setProject(project).build();
        Bug expectedBug3 = new Bug.Builder().setName("name3").setDescription("description3").setProject(project).build();
        project.addBugs(Set.of(expectedBug1, expectedBug2, expectedBug3));
        // Act
        Set<Bug> actualBugs = project.getBugs();
        Set<Bug> bugProjectBugs1 = expectedBug1.getProject().getBugs();
        Set<Bug> bugProjectBugs2 = expectedBug2.getProject().getBugs();
        Set<Bug> bugProjectBugs3 = expectedBug3.getProject().getBugs();
        // Assert
        assertNotNull(actualBugs);
        assertNotNull(bugProjectBugs1);
        assertNotNull(bugProjectBugs2);
        assertNotNull(bugProjectBugs3);
        assertTrue(actualBugs.contains(expectedBug1));
        assertTrue(actualBugs.contains(expectedBug2));
        assertTrue(actualBugs.contains(expectedBug3));
        assertTrue(bugProjectBugs1.contains(expectedBug1));
        assertTrue(bugProjectBugs2.contains(expectedBug2));
        assertTrue(bugProjectBugs3.contains(expectedBug3));
    }

    @Test
    void addBugs_WithNull_ShouldHandleGracefully() {
        // Arrange
        Project project = new Project.Builder()
                .setName("project")
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
                .setName("project")
                .build();
        Bug bug = new Bug.Builder().setName("bug1").build();
        project.addBug(bug);
        // Act
        project.addBugs(Set.of(bug));
        // Assert
        assertEquals(1, project.getBugs().size());
    }

    @Test
    void resetIDGenerator_IDStartsAtHundredThousand_IDGeneratorShouldBeSetAtZeroAgain() {
        // Arrange
        Project project = new Project.Builder().setName("project").build();
        Project.IDGeneratorProject = new IDGenerator(new AtomicInteger(100000));
        int expectedID = 0;
        // Act
        Project.resetIDGenerator();
        // Assert
        assertEquals(expectedID, Project.IDGeneratorProject.getCurrentID());
    }

    @Test
    void validate_CreateProjectWithNameNull_ThrowsValidationException() {
        // Arrange
        int expectedNumberOfErrors = 1;
        String expectedName = null;
        Project expectedProject = new Project.Builder().setName(expectedName).build();
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
        Project expectedProject = new Project.Builder().setName(expectedName).build();
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
        Project expectedProject = new Project.Builder().setName(expectedName).build();
        // Act
        // Assert
        ValidationException exception = assertThrows(ValidationException.class, expectedProject::validate);
        assertEquals(expectedNumberOfErrors, exception.getValidationErrors().size());

    }

    @Test
    void validate_WithValidName_ShouldNotThrowException() {
        // Arrange
        Project project = new Project.Builder()
                .setName("validName")
                .build();
        // Act & Assert
        assertDoesNotThrow(project::validate);
    }
}