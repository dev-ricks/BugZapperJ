package com.github.devricks.bugzapperj.microservice.bug.repository;

import com.github.devricks.bugzapperj.microservice.bug.exception.BugAlreadyExistsException;
import com.github.devricks.bugzapperj.microservice.bug.exception.NotABugEntityException;
import com.github.devricks.bugzapperj.microservice.bug.model.Bug;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.properties.PropertyMapping;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@PropertyMapping("spring")
@DataJpaTest
class BugRepositoryTests {

    private final BugRepository bugRepository;

    private Bug testBug;
    private List<Bug> testBugs;

    @Autowired
    public BugRepositoryTests(BugRepository bugRepository) {
        this.bugRepository = bugRepository;
    }

    @BeforeEach
    void setUp() {
        testBug = new Bug("Test Bug", "This is a test bug.");
        testBugs = List.of(new Bug("Test Bug 1", "This is a test bug 1."), new Bug("Test Bug 2", "This is a test bug 2."));
        bugRepository.save(testBug);
        bugRepository.saveAll(testBugs);
        bugRepository.flush();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll_ThreeBugs_ShouldReturnListOfAllBugResources() {
        List<Bug> bugs = bugRepository.findAll();
        assertEquals(3, bugs.size());
    }

    @Test
    void findAll_None_ShouldReturnEmptyList() {
        bugRepository.deleteAll();
        List<Bug> bugs = bugRepository.findAll();
        assertEquals(0, bugs.size());
    }

    @Test
    void findById_ValidBugId_ShouldReturnBugResource() {
        Bug bug = bugRepository.findById(testBug.getId()).orElse(null);
        assertEquals(testBug, bug);
    }

    @Test
    void findById_NegativeBugId_ShouldReturnNull() {
        Bug bug = bugRepository.findById(-1).orElse(null);
        assertNull(bug);
    }

    @Test
    void findById_NullBugId_ShouldThrowInvalidDataAccessApiUsageException() {
        // Have to look into this one, not sure if this is the correct exception
        assertThrows(InvalidDataAccessApiUsageException.class, () ->
                bugRepository.findById(null)
        );
    }

    @Test
    void findById_NonExistentBugId_ShouldReturnOptionalEmpty() {
        Optional<Bug> bug = bugRepository.findById(100);
        assertFalse(bug.isPresent());
    }

    @Test
    void findById_EmptyBugId_ShouldReturnNull() {
        Bug bug = bugRepository.findById(0).orElse(null);
        assertNull(bug);
    }

    @Test
    void existsById_ValidBugId_ShouldReturnTrue() {
        boolean exists = bugRepository.existsById(testBug.getId());
        assertTrue(exists);
    }

    @Test
    void existsById_NonExistentBugId_ShouldReturnFalse() {
        boolean exists = bugRepository.existsById(100);
        assertFalse(exists);
    }

    @Test
    void existsById_EmptyBugId_ShouldReturnFalse() {
        boolean exists = bugRepository.existsById(0);
        assertFalse(exists);
    }

    @Test
    void existsById_NegativeBugId_ShouldReturnFalse() {
        boolean exists = bugRepository.existsById(-1);
        assertFalse(exists);
    }

    @Test
    void save_ValidBug_ShouldReturnSavedBug() {
        Bug bug = new Bug("Test Bug 3", "This is a test bug 3.");
        Bug savedBug = bugRepository.save(bug);
        bugRepository.flush();
        assertEquals(bug, savedBug);
    }

    @Test
    void save_NullBug_ShouldThrowInvalidDataAccessApiUsageException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            bugRepository.save(null);
            bugRepository.flush();
        });
    }

    @Test
    void save_EmptyBug_ShouldThrowConstraintViolationException() {
        Bug bug = new Bug();
        assertThrows(ConstraintViolationException.class, () -> {
            bugRepository.save(bug);
            bugRepository.flush();
        });
    }

    @Test
    void save_NullBugName_ShouldThrowDataConstraintViolationException() {
        Bug bug = new Bug(null, "This is a test bug 3.");
        assertThrows(ConstraintViolationException.class, () -> {
            bugRepository.saveAndFlush(bug);
        });
    }

    @Test
    void save_EmptyBugName_ShouldThrowInvalidConstraintViolationException() {
        Bug bug = new Bug("", "This is a test bug 3.");
        assertThrows(ConstraintViolationException.class, () -> {
            bugRepository.saveAndFlush(bug);
        });
    }

    @Test
    void save_NullBugDescription_ShouldThrowConstraintViolationException() {
        Bug bug = new Bug("Test Bug 3", null);
        assertThrows(ConstraintViolationException.class, () -> {
            bugRepository.save(bug);
            bugRepository.flush();
        });
    }

    @Test
    void save_EmptyBugDescription_ShouldThrowConstraintViolationException() {
        Bug bug = new Bug("Test Bug 3", "");
        assertThrows(ConstraintViolationException.class, () -> {
            bugRepository.save(bug);
            bugRepository.flush();
        });
    }

    @Test
    void save_EmptyBugNameAndDescription_ShouldThrowInvalidDataConstraintViolationException() {
        Bug bug = new Bug("", "");
        assertThrows(ConstraintViolationException.class, () -> {
            bugRepository.saveAndFlush(bug);
        });
    }

    @Test
    void save_NullBugNameAndDescription_ShouldThrowConstraintViolationException() {
        Bug bug = new Bug(null, null);
        assertThrows(ConstraintViolationException.class, () -> {
            bugRepository.save(bug);
            bugRepository.flush();
        });
    }

    @Test
    void save_ExistingBug_ShouldMergeTheResourceWithExistingResource() {
        Bug bug = new Bug("Test Bug", "This is a test bug.");
        bug.setId(testBug.getId());
        Bug savedBug = bugRepository.save(bug);
        bugRepository.flush();
        assertEquals(bug, savedBug);
    }

    @Test
    void duplicateNotAllowedToSave_ValidBug_ShouldReturnedSavedBugResource() {
        Bug bug = new Bug("Test Bug 3", "This is a test bug 3.");
        Bug savedBug = bugRepository.duplicateNotAllowedToSave(bug);
        bugRepository.flush();
        int createdId = savedBug.getId();
        bug.setId(createdId);
        assertEquals(bug, savedBug);
    }

    @Test
    void duplicateNotAllowedToSave_NullBug_ShouldThrowNotABugEntityException() {
        assertThrows(NotABugEntityException.class, () -> {
            bugRepository.duplicateNotAllowedToSave(null);
            bugRepository.flush();
        });
    }

    @Test
    void duplicateNotAllowedToSave_EmptyBug_ShouldThrowConstraintViolationException() {
        Bug bug = new Bug();
        assertThrows(ConstraintViolationException.class, () -> {
            bugRepository.duplicateNotAllowedToSave(bug);
            bugRepository.flush();
        });
    }

    @Test
    void duplicateNotAllowedToSave_NullBugName_ShouldThrowDataConstraintViolationException() {
        Bug bug = new Bug(null, "This is a test bug 3.");
        assertThrows(ConstraintViolationException.class, () -> {
            bugRepository.duplicateNotAllowedToSave(bug);
            bugRepository.flush();
        });
    }

    @Test
    void duplicateNotAllowedToSave_EmptyBugName_ShouldThrowInvalidConstraintViolationException() {
        Bug bug = new Bug("", "This is a test bug 3.");
        assertThrows(ConstraintViolationException.class, () -> {
            bugRepository.duplicateNotAllowedToSave(bug);
            bugRepository.flush();
        });
    }

    @Test
    void duplicateNotAllowedToSave_NullBugDescription_ShouldThrowConstraintViolationException() {
        Bug bug = new Bug("Test Bug 3", null);
        assertThrows(ConstraintViolationException.class, () -> {
            bugRepository.duplicateNotAllowedToSave(bug);
            bugRepository.flush();
        });
    }

    @Test
    void duplicateNotAllowedToSave_EmptyBugDescription_ShouldThrowConstraintViolationException() {
        Bug bug = new Bug("Test Bug 3", "");
        assertThrows(ConstraintViolationException.class, () -> {
            bugRepository.duplicateNotAllowedToSave(bug);
            bugRepository.flush();
        });
    }

    @Test
    void duplicateNotAllowedToSave_EmptyBugNameAndDescription_ShouldThrowInvalidDataConstraintViolationException() {
        Bug bug = new Bug("", "");
        assertThrows(ConstraintViolationException.class, () -> {
            bugRepository.duplicateNotAllowedToSave(bug);
            bugRepository.flush();
        });
    }

    @Test
    void duplicateNotAllowedToSave_NullBugNameAndDescription_ShouldThrowConstraintViolationException() {
        Bug bug = new Bug(null, null);
        assertThrows(ConstraintViolationException.class, () -> {
            bugRepository.duplicateNotAllowedToSave(bug);
            bugRepository.flush();
        });
    }

    @Test
    void duplicateNotAllowedToSave_ExistingBug_ShouldMergeTheResourceWithExistingResource() {
        Bug bug = new Bug("Test Bug", "This is a test bug.");
        bug.setId(testBug.getId());
        assertThrows(BugAlreadyExistsException.class, () -> {
            bugRepository.duplicateNotAllowedToSave(bug);
            bugRepository.flush();
        });
    }
}