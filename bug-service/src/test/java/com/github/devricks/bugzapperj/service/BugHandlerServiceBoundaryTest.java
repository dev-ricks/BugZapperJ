package com.github.devricks.bugzapperj.service;

import com.github.devricks.bugzapperj.data.InputData;
import com.github.devricks.bugzapperj.presenter.Presenter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import testing.data.InputDataForBugEntities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.verify;

@TestInstance(Lifecycle.PER_CLASS)
public interface BugHandlerServiceBoundaryTest {

    Presenter getHandlerServicePresenterMock();

    BugHandlerService createInstance();

    Integer getExistingBugId();

    void setExistingBugId(Integer existingBugId);

    @Test
    default void boundary_InactivateBug_GivenValidInputData_ThenPresenterIsCalledOnceAndPassedOutputDataWithSuccessStatus() {
        BugHandlerService whenBugHandlerServiceBoundary = createInstance();
        InputData givenInputData = InputDataForBugEntities.findValidInputData(getExistingBugId());
        whenBugHandlerServiceBoundary.inactivateBug(givenInputData);
        verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
            assertEquals("SUCCESS", outputData.status);
            return true;
        }));

    }

    @Test
    default void boundary_InactivateBug_GivenInvalidInputData_ThenPresenterIsCalledOnceAndPassedOutputDataWithFailedStatus() {
        BugHandlerService whenBugHandlerServiceBoundary = createInstance();
        whenBugHandlerServiceBoundary.inactivateBug(InputDataForBugEntities.findInvalidInputData(100));
        verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
            assertEquals("FAILED", outputData.status);
            return true;
        }));
    }

    @Test
    default void boundary_FindBug_GivenValidInputData_ThenPresenterIsCalledOnceAndPassedOutputDataWithSuccessStatus() {
        BugHandlerService whenBugHandlerServiceBoundary = createInstance();
        InputData givenInputData = InputDataForBugEntities.findValidInputData(getExistingBugId());
        whenBugHandlerServiceBoundary.findBug(givenInputData);
        verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
            assertEquals("SUCCESS", outputData.status);
            return true;
        }));
    }

    @Test
    default void boundary_FindBug_GivenInvalidInputData_ThenPresenterIsCalledOnceAndPassedOutputDataWithFailedStatus() {
        BugHandlerService whenBugHandlerServiceBoundary = createInstance();
        whenBugHandlerServiceBoundary.findBug(InputDataForBugEntities.findInvalidInputData(100));
        verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
            assertEquals("FAILED", outputData.status);
            return true;
        }));
    }

    @Test
    default void boundary_CreateBug_GivenValidInputData_ThenPresenterIsCalledOnceAndPassedOutputDataWithSuccessStatus() {
        BugHandlerService whenBugHandlerServiceBoundary = createInstance();
        whenBugHandlerServiceBoundary.createBug(InputDataForBugEntities.createValidInputData());
        verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
            assertEquals("SUCCESS", outputData.status);
            return true;
        }));
    }

    @Test
    default void boundary_CreateBug_GivenInvalidInputData_ThenPresenterIsCalledOnceAndPassedOutputDataWithFailedStatus() {
        BugHandlerService whenBugHandlerServiceBoundary = createInstance();
        whenBugHandlerServiceBoundary.createBug(InputDataForBugEntities.createInputDataWithAllNullValues());
        verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
            assertEquals("FAILED", outputData.status);
            return true;
        }));
    }

    @Test
    default void boundary_UpdateBug_GivenValidInputData_ThenPresenterIsCalledOnceAndPassedOutputDataWithSuccessStatus() {
        BugHandlerService whenBugHandlerServiceBoundary = createInstance();
        InputData givenInputData = InputDataForBugEntities.updateValidInputData(getExistingBugId());
        whenBugHandlerServiceBoundary.updateBug(givenInputData);
        verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
            assertEquals("SUCCESS", outputData.status);
            return true;
        }));
    }

    @Test
    default void boundary_UpdateBug_GivenAllNullInputDataButLoadedBugHasValidData_ThenPresenterIsCalledOnceAndPassedOutputDataWithSuccessStatus() {
        BugHandlerService whenBugHandlerServiceBoundary = createInstance();
        InputData givenInputData = InputDataForBugEntities.updateInputDataWithAllNullValues(getExistingBugId());
        whenBugHandlerServiceBoundary.updateBug(givenInputData);
        verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
            assertEquals("SUCCESS", outputData.status);
            return true;
        }));
    }

    @Test
    default void boundary_UpdateBug_GivenAllEmptyInputDataButLoadedBugHasValidData_ThenPresenterIsCalledOnceAndPassedOutputDataWithFailedStatus() {
        BugHandlerService whenBugHandlerServiceBoundary = createInstance();
        InputData givenInputData = InputDataForBugEntities.updateInputDataWithAllEmptyValues(getExistingBugId());
        whenBugHandlerServiceBoundary.updateBug(givenInputData);
        verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
            assertEquals("FAILED", outputData.status);
            return true;
        }));
    }
}
