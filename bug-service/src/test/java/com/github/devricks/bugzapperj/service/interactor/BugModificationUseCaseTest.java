package com.github.devricks.bugzapperj.service.interactor;

import com.github.devricks.bugzapperj.data.InputData;
import com.github.devricks.bugzapperj.data.OutputData;
import com.github.devricks.bugzapperj.entity.Bug;
import org.junit.jupiter.api.Test;
import testing.data.InputDataForBugEntities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BugModificationUseCaseTest {

    private final BugModificationUseCase whenBugModificationUseCase = new BugModificationUseCase();

    @Test
    void createBug_GivenInputDataIsValid_ThenReturnDataWithStatusSuccess() {
        InputData givenInputData = InputDataForBugEntities.createValidInputData();
        OutputData thenOutputData = whenBugModificationUseCase.createBug(givenInputData);
        assertEquals(givenInputData.name, thenOutputData.name);
        assertEquals(givenInputData.description, thenOutputData.description);
        assertEquals(givenInputData.project, thenOutputData.project);
        assertEquals("SUCCESS", thenOutputData.status);
    }

    @Test
    void createBug_GivenInputDataIsNullName_ThenReturnDataWithStatusFailed() {
        InputData givenInputData = InputDataForBugEntities.createInputDataWithNullName();
        OutputData thenOutputData = whenBugModificationUseCase.createBug(givenInputData);
        assertEquals("FAILED", thenOutputData.status);
    }

    @Test
    void createBug_GivenInputDataIsNullDescription_ThenReturnDataWithStatusFailed() {
        InputData givenInputData = InputDataForBugEntities.createInputDataWithNullDescription();
        OutputData thenOutputData = whenBugModificationUseCase.createBug(givenInputData);
        assertEquals("FAILED", thenOutputData.status);
    }

    @Test
    void createBug_GivenInputDataIsNullProject_ThenReturnDataWithStatusFailed() {
        InputData givenInputData = InputDataForBugEntities.createInputDataWithNullProject();
        OutputData thenOutputData = whenBugModificationUseCase.createBug(givenInputData);
        assertEquals("FAILED", thenOutputData.status);
    }

    @Test
    void createBug_GivenInputDataIsNullBothNameAndDescription_ThenReturnDataWithStatusFailed() {
        InputData givenInputData = InputDataForBugEntities.createInputDataWithNameAndDescriptionNull();
        OutputData thenOutputData = whenBugModificationUseCase.createBug(givenInputData);
        assertEquals("FAILED", thenOutputData.status);
    }

    @Test
    void createBug_GivenInputDataIsNullForNameAndDescriptionAndProject_ThenReturnDataWithStatusFailed() {
        InputData givenInputData = InputDataForBugEntities.createInputDataWithAllNullValues();
        OutputData thenOutputData = whenBugModificationUseCase.createBug(givenInputData);
        assertEquals("FAILED", thenOutputData.status);
    }

    @Test
    void createBug_GivenTwoBugsAreCreated_ThenReturnBugsWithDifferentIdsWithinOneOfEachOther() {
        InputData givenInputData1 = InputDataForBugEntities.createValidInputData();
        InputData givenInputData2 = InputDataForBugEntities.createValidInputData();
        OutputData thenOutputData1 = whenBugModificationUseCase.createBug(givenInputData1);
        OutputData thenOutputData2 = whenBugModificationUseCase.createBug(givenInputData2);
        assertNotEquals(thenOutputData1.id, thenOutputData2.id);
        assertEquals(1, Math.abs(thenOutputData1.id - thenOutputData2.id));
    }

    @Test
    void createBug_GivenInputDataIsValid_ThenReturnBugDataWithIncrementedId() {
        Bug.resetIDGenerator();
        InputData givenInputData = InputDataForBugEntities.createValidInputData();
        OutputData thenOutputData = whenBugModificationUseCase.createBug(givenInputData);
        assertEquals(1, thenOutputData.id);
    }

    @Test
    void createBug_GivenInputDataFailsValidation_ThenIncrementIdShouldNotBeGenerated() {
        Bug.resetIDGenerator();
        InputData givenInputData = InputDataForBugEntities.createInputDataWithNullName();
        OutputData thenOutputData = whenBugModificationUseCase.createBug(givenInputData);
        assertEquals(0, thenOutputData.id);
    }
}