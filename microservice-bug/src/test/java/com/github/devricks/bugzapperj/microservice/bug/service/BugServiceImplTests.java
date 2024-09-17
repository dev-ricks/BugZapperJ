package com.github.devricks.bugzapperj.microservice.bug.service;

import com.github.devricks.bugzapperj.microservice.bug.exception.BugAlreadyExistsException;
import com.github.devricks.bugzapperj.microservice.bug.exception.BugNotFoundException;
import com.github.devricks.bugzapperj.microservice.bug.exception.InvalidBugIdException;
import com.github.devricks.bugzapperj.microservice.bug.exception.NullBugDataException;
import com.github.devricks.bugzapperj.microservice.bug.model.Bug;
import com.github.devricks.bugzapperj.microservice.bug.repository.BugRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BugServiceImplTests {

    @Mock
    private BugRepository stubBugRepository;

    @InjectMocks
    private BugServiceImpl bugService;

    @Test
    void getBugById_ValidId_ShouldReturnABugResourceWithDataFromThatId() {
        // Arrange
        int validId = 1;
        Bug expectedBug = new Bug("Sample Bug", "Sample Description");
        expectedBug.setId(validId);
        when(stubBugRepository.findById(validId)).thenReturn(Optional.of(expectedBug));
        // Act
        Bug actualBug = bugService.getBugById(validId);
        // Assert
        assertNotNull(actualBug);
        assertEquals(expectedBug.getId(), actualBug.getId());
        assertEquals(expectedBug.getName(), actualBug.getName());
        assertEquals(expectedBug.getDescription(), actualBug.getDescription());
    }

    @Test
    void getBugById_InvalidId_ShouldThrowInvalidBugIdException() {
        // Arrange
        int invalidId = -1;
        // Act & Assert
        assertThrows(InvalidBugIdException.class, () -> bugService.getBugById(invalidId));
    }

    @Test
    void getBugById_BugNotFound_ShouldThrowBugNotFoundException() {
        // Arrange
        int validId = 1;
        when(stubBugRepository.findById(validId)).thenReturn(Optional.empty());
        // Act & Assert
        assertThrows(BugNotFoundException.class, () -> bugService.getBugById(validId));
    }

    @Test
    void getBugById_NullId_ShouldThrowInvalidBugIdException() {
        // Arrange
        Integer nullId = null;
        // Act & Assert
        assertThrows(InvalidBugIdException.class, () -> bugService.getBugById(nullId));
    }

    @Test
    void getAllBugs_None_ShouldReturnAllBugResourcesInExistence() {
        // Arrange
        Bug bug1 = new Bug("Sample Bug 1", "Sample Description 1");
        bug1.setId(1);
        Bug bug2 = new Bug("Sample Bug 2", "Sample Description 2");
        bug2.setId(2);
        when(stubBugRepository.findAll()).thenReturn(List.of(bug1, bug2));
        // Act
        List<Bug> actualBugs = bugService.getAllBugs();
        // Assert
        assertNotNull(actualBugs);
        assertEquals(2, actualBugs.size());
        assertEquals(bug1.getId(), actualBugs.get(0).getId());
        assertEquals(bug1.getName(), actualBugs.get(0).getName());
        assertEquals(bug1.getDescription(), actualBugs.get(0).getDescription());
        assertEquals(bug2.getId(), actualBugs.get(1).getId());
        assertEquals(bug2.getName(), actualBugs.get(1).getName());
        assertEquals(bug2.getDescription(), actualBugs.get(1).getDescription());
    }

    @Test
    void getAllBugs_None_ShouldReturnEmptyList() {
        // Arrange
        when(stubBugRepository.findAll()).thenReturn(List.of());
        // Act
        List<Bug> actualBugs = bugService.getAllBugs();
        // Assert
        assertNotNull(actualBugs);
        assertEquals(0, actualBugs.size());
    }

    @Test
    void createBug_CompleteNewBugData_ShouldReturnBugResourceWithData() {
        // Arrange
        Bug newBug = new Bug("Sample Bug", "Sample Description");
        newBug.setId(1);
        newBug.setBugStatus(1);
        newBug.setType(1);
        newBug.setPriority(1);
        newBug.setAssigneeId(1);
        newBug.setReporterId(1);
        when(stubBugRepository.save(newBug)).thenReturn(newBug);
        // Act
        Bug actualBug = bugService.createBug(newBug);
        // Assert
        assertNotNull(actualBug);
        assertEquals(newBug.getId(), actualBug.getId());
        assertEquals(newBug.getName(), actualBug.getName());
        assertEquals(newBug.getDescription(), actualBug.getDescription());
        assertEquals(newBug.getBugStatus(), actualBug.getBugStatus());
        assertEquals(newBug.getType(), actualBug.getType());
        assertEquals(newBug.getPriority(), actualBug.getPriority());
        assertEquals(newBug.getAssigneeId(), actualBug.getAssigneeId());
        assertEquals(newBug.getReporterId(), actualBug.getReporterId());
    }

    @Test
    void createBug_NullBugData_ShouldThrowNullBugDataException() {
        // Arrange
        Bug nullBug = null;
        // Act & Assert
        assertThrows(NullBugDataException.class, () -> bugService.createBug(nullBug));
    }

    @Test
    void createBug_NullBugName_ShouldThrowNullBugDataException() {
        // Arrange
        Bug nullBugName = new Bug(null, "Sample Description");
        // Act & Assert
        assertThrows(NullBugDataException.class, () -> bugService.createBug(nullBugName));
    }

    @Test
    void createBug_NullBugDescription_ShouldThrowNullBugDataException() {
        // Arrange
        Bug nullBugDescription = new Bug("Sample Bug", null);
        // Act & Assert
        assertThrows(NullBugDataException.class, () -> bugService.createBug(nullBugDescription));
    }

    @Test
    void createBug_BugAlreadyExists_ShouldThrowBugAlreadyExistsException() {
        // Arrange
        Bug existingBug = new Bug("Sample Bug", "Sample Description");
        existingBug.setId(1);
        when(stubBugRepository.existsById(existingBug.getId())).thenReturn(true);
        // Act & Assert
        assertThrows(BugAlreadyExistsException.class, () -> bugService.createBug(existingBug));
    }

    @Test
    void createBug_ZeroBugId_ShouldThrowNullBugDataException() {
        // Arrange
        Bug invalidBugId = new Bug("Sample Bug", "Sample Description");
        invalidBugId.setId(0);
        // Act & Assert
        assertThrows(NullBugDataException.class, () -> bugService.createBug(invalidBugId));
    }

    @Test
    void createBug_NegativeBugId_ShouldThrowNullBugDataException() {
        // Arrange
        Bug invalidBugId = new Bug("Sample Bug", "Sample Description");
        invalidBugId.setId(-1);
        // Act & Assert
        assertThrows(NullBugDataException.class, () -> bugService.createBug(invalidBugId));
    }

    @Test
    void deleteBug_ValidId_ShouldNotThrowAnyExceptions() {
        // Arrange
        int validId = 1;
        when(stubBugRepository.existsById(validId)).thenReturn(true);
        // Act & Assert
        assertDoesNotThrow(() -> bugService.deleteBug(validId));
    }

    @Test
    void deleteBug_NegativeId_ShouldThrowInvalidBugIdException() {
        // Arrange
        int invalidId = -1;
        // Act & Assert
        assertThrows(InvalidBugIdException.class, () -> bugService.deleteBug(invalidId));
    }

    @Test
    void deleteBug_ZeroId_ShouldThrowInvalidBugIdException() {
        // Arrange
        int invalidId = 0;
        // Act & Assert
        assertThrows(InvalidBugIdException.class, () -> bugService.deleteBug(invalidId));
    }

    @Test
    void deleteBug_BugNotFound_ShouldThrowBugNotFoundException() {
        // Arrange
        int validId = 1;
        when(stubBugRepository.existsById(validId)).thenReturn(false);
        // Act & Assert
        assertThrows(BugNotFoundException.class, () -> bugService.deleteBug(validId));
    }

    @Test
    void updateBug_ValidId_ShouldReturnBugResourceWithUpdatedData() {
        // Arrange
        Bug updatedBug = new Bug("Sample Bug", "Sample Description");
        updatedBug.setId(1);
        updatedBug.setBugStatus(1);
        updatedBug.setType(1);
        updatedBug.setPriority(1);
        updatedBug.setAssigneeId(1);
        updatedBug.setReporterId(1);
        when(stubBugRepository.existsById(updatedBug.getId())).thenReturn(true);
        when(stubBugRepository.save(updatedBug)).thenReturn(updatedBug);
        // Act
        Bug actualBug = bugService.updateBug(updatedBug);
        // Assert
        assertNotNull(actualBug);
        assertEquals(updatedBug.getId(), actualBug.getId());
        assertEquals(updatedBug.getName(), actualBug.getName());
        assertEquals(updatedBug.getDescription(), actualBug.getDescription());
        assertEquals(updatedBug.getBugStatus(), actualBug.getBugStatus());
        assertEquals(updatedBug.getType(), actualBug.getType());
        assertEquals(updatedBug.getPriority(), actualBug.getPriority());
        assertEquals(updatedBug.getAssigneeId(), actualBug.getAssigneeId());
        assertEquals(updatedBug.getReporterId(), actualBug.getReporterId());
    }

    @Test
    void updateBug_NullBugData_ShouldThrowNullBugDataException() {
        // Arrange
        Bug nullBug = null;
        // Act & Assert
        assertThrows(NullBugDataException.class, () -> bugService.updateBug(nullBug));
    }

    @Test
    void updateBug_NullBugName_ShouldThrowNullBugDataException() {
        // Arrange
        Bug nullBugName = new Bug(null, "Sample Description");
        // Act & Assert
        assertThrows(NullBugDataException.class, () -> bugService.updateBug(nullBugName));
    }

    @Test
    void updateBug_NullBugDescription_ShouldThrowNullBugDataException() {
        // Arrange
        Bug nullBugDescription = new Bug("Sample Bug", null);
        // Act & Assert
        assertThrows(NullBugDataException.class, () -> bugService.updateBug(nullBugDescription));
    }

    @Test
    void updateBug_BugNotFound_ShouldThrowBugNotFoundException() {
        // Arrange
        Bug nonExistentBug = new Bug("Sample Bug", "Sample Description");
        nonExistentBug.setId(1);
        when(stubBugRepository.existsById(nonExistentBug.getId())).thenReturn(false);
        // Act & Assert
        assertThrows(BugNotFoundException.class, () -> bugService.updateBug(nonExistentBug));
    }

    @Test
    void updateBug_ZeroBugId_ShouldThrowNullBugDataException() {
        // Arrange
        Bug invalidBugId = new Bug("Sample Bug", "Sample Description");
        invalidBugId.setId(0);
        // Act & Assert
        assertThrows(NullBugDataException.class, () -> bugService.updateBug(invalidBugId));
    }

    @Test
    void updateBug_NegativeBugId_ShouldThrowNullBugDataException() {
        // Arrange
        Bug invalidBugId = new Bug("Sample Bug", "Sample Description");
        invalidBugId.setId(-1);
        // Act & Assert
        assertThrows(NullBugDataException.class, () -> bugService.updateBug(invalidBugId));
    }

    @Test
    void updateBug_NullBugId_ShouldThrowNullBugDataException() {
        // Arrange
        Bug nullBugId = new Bug("Sample Bug", "Sample Description");
        nullBugId.setId(0);
        // Act & Assert
        assertThrows(NullBugDataException.class, () -> bugService.updateBug(nullBugId));
    }
}