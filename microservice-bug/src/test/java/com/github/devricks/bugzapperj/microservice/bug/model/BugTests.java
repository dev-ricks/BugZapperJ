package com.github.devricks.bugzapperj.microservice.bug.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BugTests {

    @Test
    void toString_None_ShouldReturnStringRepresentationOfResource() {
        // Arrange
        Bug bug = new Bug();
        bug.setId(1);
        bug.setName("name");
        bug.setDescription("description");
        bug.setPriority(1);
        bug.setBugStatus(1);
        bug.setType(1);
        bug.setAssigneeId(1);
        bug.setReporterId(1);
        String expected = "Bug{name='name', description='description', id=1, type=1, priority=1, bugStatus=1, reporterId=1, assigneeId=1}";
        // Act
        String actual = bug.toString();
        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void equals_DataSameInBugObject_ShouldReturnTrue() {
        // Arrange
        Bug bug1 = new Bug();
        bug1.setId(1);
        bug1.setName("name");
        bug1.setDescription("description");
        bug1.setPriority(1);
        bug1.setBugStatus(1);
        bug1.setType(1);
        bug1.setAssigneeId(1);
        bug1.setReporterId(1);
        Bug bug2 = new Bug();
        bug2.setId(1);
        bug2.setName("name");
        bug2.setDescription("description");
        bug2.setPriority(1);
        bug2.setBugStatus(1);
        bug2.setType(1);
        bug2.setAssigneeId(1);
        bug2.setReporterId(1);
        // Act
        boolean actual = bug1.equals(bug2);
        // Assert
        assertTrue(actual);
    }

    @Test
    void equals_DataDifferentInBugObject_ShouldReturnFalse() {
        // Arrange
        Bug bug1 = new Bug();
        bug1.setId(1);
        bug1.setName("name");
        bug1.setDescription("description");
        bug1.setPriority(1);
        bug1.setBugStatus(1);
        bug1.setType(1);
        bug1.setAssigneeId(1);
        bug1.setReporterId(1);
        Bug bug2 = new Bug();
        bug2.setId(2);
        bug2.setName("name");
        bug2.setDescription("description");
        bug2.setPriority(1);
        bug2.setBugStatus(1);
        bug2.setType(1);
        bug2.setAssigneeId(1);
        bug2.setReporterId(1);
        // Act
        boolean actual = bug1.equals(bug2);
        // Assert
        assertFalse(actual);
    }

    @Test
    void hashCode_BugObject_ShouldReturnHashCodeAsExpected() {
        // Arrange
        Bug bug = new Bug();
        bug.setId(1);
        bug.setName("name");
        bug.setDescription("description");
        bug.setPriority(1);
        bug.setBugStatus(1);
        bug.setType(1);
        bug.setAssigneeId(1);
        bug.setReporterId(1);
        int expected = 1032065394;
        // Act
        int actual = bug.hashCode();
        // Assert
        assertEquals(expected, actual);
    }
}