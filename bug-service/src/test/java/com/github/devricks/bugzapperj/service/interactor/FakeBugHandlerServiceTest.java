package com.github.devricks.bugzapperj.service.interactor;

import com.github.devricks.bugzapperj.data.InputData;
import com.github.devricks.bugzapperj.data.OutputData;
import com.github.devricks.bugzapperj.entity.Bug;
import com.github.devricks.bugzapperj.presenter.Presenter;
import com.github.devricks.bugzapperj.service.AbstractBugHandlerServiceTest;
import com.github.devricks.bugzapperj.service.BugHandlerService;
import com.github.devricks.bugzapperj.storage.DataStorageGateway;
import com.github.devricks.bugzapperj.storage.memory.FakeDataStorageGateway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import testing.data.OutputDataForBugEntities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FakeBugHandlerServiceTest extends AbstractBugHandlerServiceTest {

    private FakeBugHandlerService fakeBugHandlerService;
    private DataStorageGateway<Bug> dataStorageGateway;

    @BeforeEach
    public void setUpExistingBug() {
        fakeBugHandlerService = (FakeBugHandlerService) createInstance();
        OutputDataForBugEntities.createExistingBug(fakeBugHandlerService);
        setExistingBugId(Bug.IDGenerator.getCurrentID());
        fakeBugHandlerService = null;
        fakeBugHandlerService = (FakeBugHandlerService) createInstance();
    }

    @AfterEach
    public void tearDown() {
        fakeBugHandlerService = null;
        Bug.resetIDGenerator();
    }

    @Override
    public BugHandlerService createInstance() {
        presenterMock = mock(Presenter.class);
        if (dataStorageGateway == null)
            dataStorageGateway = new FakeDataStorageGateway();
        return new FakeBugHandlerService(presenterMock, dataStorageGateway);
    }

    @Nested
    class FakeBugHandlerTest {

        @Mock
        private Presenter presenter;

        @Mock
        private DataStorageGateway<Bug> dataStorageGateway;

        @InjectMocks
        private FakeBugHandlerService fakeBugHandlerService;

        @Captor
        private ArgumentCaptor<OutputData> outputDataCaptor;

        private InputData inputData;

        @BeforeEach
        void setUp() {
            inputData = new InputData();
        }

        @Test
        void createBug_ValidInput_ShouldSaveBug() {
            inputData.name = "Valid Bug";
            inputData.description = "Valid Description";
            when(dataStorageGateway.save(any(Bug.class))).thenReturn(true);
            fakeBugHandlerService.createBug(inputData);
            verify(dataStorageGateway).save(any(Bug.class));
            verify(presenter).present(outputDataCaptor.capture());
            OutputData outputData = outputDataCaptor.getValue();
            assertEquals(true, outputData.persisted);
        }

        @Test
        void createBug_InvalidInput_ShouldNotSaveBug() {
            inputData.name = null; // Invalid input
            fakeBugHandlerService.createBug(inputData);
            verify(dataStorageGateway, never()).save(any(Bug.class));
            verify(presenter).present(outputDataCaptor.capture());
            OutputData outputData = outputDataCaptor.getValue();
            assertEquals(false, outputData.persisted);
        }

        @Test
        void updateBug_ValidInput_ShouldUpdateBug() {
            inputData.id = 1;
            inputData.name = "Existing Bug";
            inputData.description = "Existing Description";
            InputData updateData = new InputData();
            updateData.id = 1;
            updateData.name = "Updated Bug";
            updateData.description = "Updated Description";
            Bug existingBug = new Bug.Builder().build(inputData);
            when(dataStorageGateway.find(inputData.id)).thenReturn(existingBug);
            when(dataStorageGateway.save(any(Bug.class))).thenReturn(true);
            fakeBugHandlerService.updateBug(updateData);
            verify(dataStorageGateway).save(any(Bug.class));
            verify(presenter).present(outputDataCaptor.capture());
            OutputData outputData = outputDataCaptor.getValue();
            assertEquals(updateData.name, outputData.name);
        }

        @Test
        void updateBug_InvalidInput_ShouldNotUpdateBug() {
            inputData.id = 1;
            inputData.name = null; // Invalid input
            InputData updateData = new InputData();
            updateData.id = 1;
            updateData.name = "Updated Bug";
            Bug existingBug = new Bug.Builder().build(inputData);
            when(dataStorageGateway.find(inputData.id)).thenReturn(existingBug);
            fakeBugHandlerService.updateBug(updateData);
            verify(dataStorageGateway, never()).save(any(Bug.class));
            verify(presenter).present(outputDataCaptor.capture());
            OutputData outputData = outputDataCaptor.getValue();
            assertEquals(inputData.name, outputData.name);
        }

        @Test
        void findBug_ValidInput_ShouldReturnBug() {
            inputData.id = 1;
            Bug existingBug = new Bug.Builder().build(inputData);
            existingBug.setId(inputData.id);
            when(dataStorageGateway.find(inputData.id)).thenReturn(existingBug);
            fakeBugHandlerService.findBug(inputData);
            verify(presenter).present(outputDataCaptor.capture());
            OutputData outputData = outputDataCaptor.getValue();
            assertEquals(inputData.id, outputData.id);
        }

        @Test
        void findBug_InvalidInput_ShouldReturnNotFound() {
            inputData.id = 1;
            when(dataStorageGateway.find(inputData.id)).thenReturn(null);
            fakeBugHandlerService.findBug(inputData);
            verify(presenter).present(outputDataCaptor.capture());
            OutputData outputData = outputDataCaptor.getValue();
            assertEquals("Bug not found.", outputData.message);
        }

        @Test
        void inactivateBug_ValidInput_ShouldInactivateBug() {
            inputData.id = 1;
            Bug existingBug = new Bug.Builder().build(inputData);
            when(dataStorageGateway.find(inputData.id)).thenReturn(existingBug);
            fakeBugHandlerService.inactivateBug(inputData);
            verify(dataStorageGateway).save(any(Bug.class));
            verify(presenter).present(outputDataCaptor.capture());
            OutputData outputData = outputDataCaptor.getValue();
            assertEquals(true, outputData.inactivated);
        }

        @Test
        void inactivateBug_InvalidInput_ShouldReturnNotFound() {
            inputData.id = 1;
            when(dataStorageGateway.find(inputData.id)).thenReturn(null);
            fakeBugHandlerService.inactivateBug(inputData);
            verify(dataStorageGateway, never()).save(any(Bug.class));
            verify(presenter).present(outputDataCaptor.capture());
            OutputData outputData = outputDataCaptor.getValue();
            assertEquals("FAILED", outputData.status);
        }
    }
}