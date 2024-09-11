package com.github.devricks.bugzapperj.microservice.bug.controller;

import com.github.devricks.bugzapperj.microservice.bug.exception.BugNotFoundException;
import com.github.devricks.bugzapperj.microservice.bug.exception.InvalidBugIdException;
import com.github.devricks.bugzapperj.microservice.bug.exception.NullBugDataException;
import com.github.devricks.bugzapperj.microservice.bug.model.Bug;
import com.github.devricks.bugzapperj.microservice.bug.service.BugService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BugControllerTests {

    private final List<Bug> mockBugs;
    @Mock
    private BugService stubBugService;
    @InjectMocks
    private BugController bugController;

    public BugControllerTests() {
        Bug mockBug1 = new Bug("Sample Bug 1", "Description of the bug 1");
        mockBug1.setId(1);
        Bug mockBug2 = new Bug("Sample Bug 2", "Description of the bug 2");
        mockBug2.setId(2);
        mockBugs = new ArrayList<>();
        mockBugs.add(mockBug1);
        mockBugs.add(mockBug2);
    }

    @Test
    void getBugs_Many_ShouldBeOkResponseWithBugsData() {
        // Arrange
        when(stubBugService.getAllBugs()).thenReturn(mockBugs);
        // Act
        ResponseEntity<?> response = bugController.getBugs();
        // Assert
        assertNotNull(response.getBody());
        assertEquals(1, ((Map<?, ?>) response.getBody()).get("status"));
        assertEquals(mockBugs, ((Map<?, ?>) response.getBody()).get("data"));
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void getBugs_None_ShouldBeOkResponseWithNoBugsData() {
        // Arrange
        when(stubBugService.getAllBugs()).thenReturn(new ArrayList<>());
        // Act
        ResponseEntity<?> response = bugController.getBugs();
        // Assert
        assertNotNull(response.getBody());
        assertEquals(1, ((Map<?, ?>) response.getBody()).get("status"));
        assertEquals(0, ((List<?>) ((Map<?, ?>) response.getBody()).get("data")).size());
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void getBugById_BugId_ShouldBeOkResponseWithBugData() {
        // Arrange
        when(stubBugService.getBugById(1)).thenReturn(mockBugs.get(0));
        // Act
        ResponseEntity<?> response = bugController.getBugById(1);
        // Assert
        assertNotNull(response.getBody());
        assertEquals(1, ((Map<?, ?>) response.getBody()).get("status"));
        assertEquals(mockBugs.get(0), ((Map<?, ?>) response.getBody()).get("data"));
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void getBugById_BugIdDNE_ShouldBeNotFound404ResponseWithStatusData() {
        // Arrange
        when(stubBugService.getBugById(1)).thenThrow(new BugNotFoundException("Bug not found"));
        // Act
        ResponseEntity<?> response = bugController.getBugById(1);
        // Assert
        assertNotNull(response.getBody());
        assertEquals(0, ((Map<?, ?>) response.getBody()).get("status"));
        assertEquals("Bug not found", ((Map<?, ?>) response.getBody()).get("message"));
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void getBugById_BugIdAsNegative_ShouldBeBadRequest400ResponseWithStatusData() {
        // Arrange
        when(stubBugService.getBugById(-1)).thenThrow(new InvalidBugIdException("Invalid bug id"));
        // Act
        ResponseEntity<?> response = bugController.getBugById(-1);
        // Assert
        assertNotNull(response.getBody());
        assertEquals(0, ((Map<?, ?>) response.getBody()).get("status"));
        assertEquals("Invalid bug id", ((Map<?, ?>) response.getBody()).get("message"));
        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void createBug_BugData_ShouldBeCreated201ResponseWithBugData() {
        // Arrange
        Bug stubBug = new Bug("Sample Bug 3", "Description of the bug 3");
        Bug mockCreatedBug = new Bug("Sample Bug 3", "Description of the bug 3");
        mockCreatedBug.setId(3);
        when(stubBugService.createBug(stubBug)).thenReturn(mockCreatedBug);
        // Act
        ResponseEntity<?> response = bugController.createBug(stubBug);
        // Assert
        assertNotNull(response.getBody());
        assertEquals(1, ((Map<?, ?>) response.getBody()).get("status"));
        assertEquals(mockCreatedBug, ((Map<?, ?>) response.getBody()).get("data"));
        assertEquals(201, response.getStatusCode().value());
    }

    @Test
    void createBug_BadBugNameData_ShouldBeBadRequest400ResponseWithStatusData() {
        // Arrange
        Bug stubBug = new Bug("", "Description of the bug 3");
        when(stubBugService.createBug(stubBug)).thenThrow(new NullBugDataException("Invalid bug data, name is required"));
        // Act
        ResponseEntity<?> response = bugController.createBug(stubBug);
        // Assert
        assertNotNull(response.getBody());
        assertEquals(0, ((Map<?, ?>) response.getBody()).get("status"));
        assertEquals("Invalid bug data, name is required", ((Map<?, ?>) response.getBody()).get("message"));
        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void createBug_BadBugDescriptionData_ShouldBeBadRequest400ResponseWithStatusData() {
        // Arrange
        Bug stubBug = new Bug("Sample Bug 3", "");
        when(stubBugService.createBug(stubBug)).thenThrow(new NullBugDataException("Invalid bug data, description is required"));
        // Act
        ResponseEntity<?> response = bugController.createBug(stubBug);
        // Assert
        assertNotNull(response.getBody());
        assertEquals(0, ((Map<?, ?>) response.getBody()).get("status"));
        assertEquals("Invalid bug data, description is required", ((Map<?, ?>) response.getBody()).get("message"));
        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void updateBug_BugData_ShouldBeOk200ResponseWithBugData() {
        // Arrange
        Bug stubBug = new Bug("Sample Bug", "Description of the bug");
        stubBug.setId(1);
        Bug mockBug = new Bug("Sample Bug", "Description of the bug");
        mockBug.setId(1);
        when(stubBugService.updateBug(stubBug)).thenReturn(mockBug);
        // Act
        ResponseEntity<?> response = bugController.updateBug(stubBug.getId(), stubBug);
        // Assert
        assertNotNull(response.getBody());
        assertEquals(1, ((Map<?, ?>) response.getBody()).get("status"));
        assertEquals(mockBug, ((Map<?, ?>) response.getBody()).get("data"));
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void updateBug_BadBugNameData_ShouldBeBadRequest400ResponseWithStatusData() {
        // Arrange
        Bug stubBug = new Bug("", "Description of the bug 1");
        when(stubBugService.updateBug(stubBug)).thenThrow(new NullBugDataException("Invalid bug data, name is required"));
        // Act
        ResponseEntity<?> response = bugController.updateBug(1, stubBug);
        // Assert
        assertNotNull(response.getBody());
        assertEquals(0, ((Map<?, ?>) response.getBody()).get("status"));
        assertEquals("Invalid bug data, name is required", ((Map<?, ?>) response.getBody()).get("message"));
        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void updateBug_BadBugDescriptionData_ShouldBeBadRequest400ResponseWithStatusData() {
        // Arrange
        Bug stubBug = new Bug("Sample Bug 1", "");
        stubBug.setId(1);
        when(stubBugService.updateBug(stubBug)).thenThrow(new NullBugDataException("Invalid bug data, description is required"));
        // Act
        ResponseEntity<?> response = bugController.updateBug(1, stubBug);
        // Assert
        assertNotNull(response.getBody());
        assertEquals(0, ((Map<?, ?>) response.getBody()).get("status"));
        assertEquals("Invalid bug data, description is required", ((Map<?, ?>) response.getBody()).get("message"));
        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void updateBug_BugData_ShouldBeNotFound404ResponseWithStatusData() {
        // Arrange
        Bug stubBug = new Bug("Sample Bug 3", "Description of the bug 3");
        when(stubBugService.updateBug(stubBug)).thenThrow(new BugNotFoundException("Bug not found"));
        // Act
        ResponseEntity<?> response = bugController.updateBug(3, stubBug);
        // Assert
        assertNotNull(response.getBody());
        assertEquals(0, ((Map<?, ?>) response.getBody()).get("status"));
        assertEquals("Bug not found", ((Map<?, ?>) response.getBody()).get("message"));
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void deleteBug_BugId_ShouldBeOk200ResponseWithStatusData() {
        // Arrange
        doNothing().when(stubBugService).deleteBug(1);
        // Act
        ResponseEntity<?> response = bugController.deleteBug(1);
        // Assert
        assertNotNull(response.getBody());
        assertEquals(1, ((Map<?, ?>) response.getBody()).get("status"));
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void deleteBug_BugId_ShouldBeNotFound404ResponseWithStatusData() {
        // Arrange
        doThrow(new BugNotFoundException("Bug not found")).when(stubBugService).deleteBug(1);
        // Act
        ResponseEntity<?> response = bugController.deleteBug(1);
        // Assert
        assertNotNull(response.getBody());
        assertEquals(0, ((Map<?, ?>) response.getBody()).get("status"));
        assertEquals("Bug not found", ((Map<?, ?>) response.getBody()).get("message"));
        assertEquals(404, response.getStatusCode().value());
    }
}
