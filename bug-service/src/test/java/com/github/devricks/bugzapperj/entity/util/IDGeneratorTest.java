package com.github.devricks.bugzapperj.entity.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IDGeneratorTest {

    IDGenerator componentToTest = new IDGenerator();

    @BeforeEach
    void setUp() {
        componentToTest.reset();
    }

    @Test
    void IDGenerator_NewGenerator_ShouldResultInIDOfZero() {
        // Arrange
        int expectedID = 0;
        // Act
        componentToTest = new IDGenerator();
        int actualID = componentToTest.getCurrentID();
        // Assert
        assertEquals(expectedID, actualID);
    }

    @Test
    void IDGenerator_NewGeneratorWithIDOfOne_ShouldResultInIDOfOne() {
        // Arrange
        int expectedID = 1;
        // Act
        componentToTest = new IDGenerator(new AtomicInteger(1));
        int actualID = componentToTest.getCurrentID();
        // Assert
        assertEquals(expectedID, actualID);
    }

    @Test
    void IDGenerator_NewGeneratorWithIDOfHundredThousand_ShouldResultInIDOfHundredThousand() {
        // Arrange
        int expectedID = 100000;
        // Act
        componentToTest = new IDGenerator(new AtomicInteger(100000));
        int actualID = componentToTest.getCurrentID();
        // Assert
        assertEquals(expectedID, actualID);
    }

    @Test
    void generateID_IDStartingAtZero_ShouldResultInIDOfOne() {
        // Arrange
        int expectedID = 1;
        // Act
        int actualID = componentToTest.generateID();
        // Assert
        assertEquals(expectedID, actualID);
    }

    @Test
    void generateID_GenerateCallHundredTimes_ShouldResultInIdOfHundred() {
        // Arrange
        int expectedID = 100;
        // Act
        for (int i = 0; i < 100; i++) {
            componentToTest.generateID();
        }
        int actualID = componentToTest.getCurrentID();
        // Assert
        assertEquals(expectedID, actualID);
    }

    @Test
    void reset_StartsWithIdAtHundredThousand_ShouldResetIDToZero() {
        // Arrange
        int expectedID = 0;
        componentToTest = new IDGenerator(new AtomicInteger(100000));
        // Act
        componentToTest.reset();
        int actualID = componentToTest.getCurrentID();
        // Assert
        assertEquals(expectedID, actualID);
    }

    @Test
    void getCurrentID_NewGenerator_ShouldResultInAnIDOfOne() {
        // Arrange
        int expectedID = 1;
        componentToTest.generateID();
        // Act
        int actualID = componentToTest.getCurrentID();
        // Assert
        assertEquals(expectedID, actualID);
    }
}