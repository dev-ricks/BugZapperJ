package com.github.devricks.bugzapperj.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HelperFunctionsTest {

    @Test
    void isBlank_GivenEmptyString_ThenReturnTrue() {
        String givenInput = "";
        boolean thenResult = HelperFunctions.isBlank(givenInput);
        assertTrue(thenResult);
    }

    @Test
    void isBlank_GivenNull_ThenReturnTrue() {
        String givenInput = null;
        boolean thenResult = HelperFunctions.isBlank(givenInput);
        assertTrue(thenResult);
    }

    @Test
    void isBlank_GivenStringWithSpaces_ThenReturnTrue() {
        String givenInput = " ";
        boolean thenResult = HelperFunctions.isBlank(givenInput);
        assertTrue(thenResult);
    }

    @Test
    void isBlank_GivenStringWithCharacters_ThenReturnFalse() {
        String givenInput = "a";
        boolean thenResult = HelperFunctions.isBlank(givenInput);
        assertFalse(thenResult);
    }
}
