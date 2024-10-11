package com.github.devricks.bugzapperj.service;

import com.github.devricks.bugzapperj.data.InputData;
import com.github.devricks.bugzapperj.data.OutputData;
import com.github.devricks.bugzapperj.service.interactor.BugModificationUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import testing.data.InputDataForBugEntities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(Lifecycle.PER_CLASS)
interface BugHandlerServiceBoundaryTest {

    BugHandlerService createInstance();

    @Test
    default void createBug_GivenValidInputData_ThenReturnsOutputDataObjectWithSuccessStatus() {
        BugHandlerService whenBugHandlerServiceBoundary = createInstance();
        InputData givenInputData = InputDataForBugEntities.createValidInputData();
        OutputData thenOutputData = whenBugHandlerServiceBoundary.createBug(givenInputData);
        assertNotNull(thenOutputData);
        assertEquals("SUCCESS", thenOutputData.status);
    }

    @Test
    default void createBug_GivenInvalidInputData_ThenReturnsOutputDataWithFailedStatus() {
        BugHandlerService whenBugHandlerServiceBoundary = createInstance();
        InputData givenInputData = InputDataForBugEntities.createInputDataWithAllNullValues();
        OutputData thenOutputData = whenBugHandlerServiceBoundary.createBug(givenInputData);
        assertNotNull(thenOutputData);
        assertNotNull(thenOutputData.status);
        assertEquals("FAILED", thenOutputData.status);
    }
}

class BugModificationUseCaseInterfaceTest implements BugHandlerServiceBoundaryTest {
    @Override
    public BugHandlerService createInstance() {
        return new BugModificationUseCase();
    }
}
