package com.github.devricks.bugzapperj.service.interactor;

import com.github.devricks.bugzapperj.data.InputData;
import com.github.devricks.bugzapperj.entity.Bug;
import com.github.devricks.bugzapperj.entity.exception.ValidationException;
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
import org.mockito.junit.jupiter.MockitoExtension;
import testing.data.InputDataForBugEntities;
import testing.data.OutputDataForBugEntities;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BugModificationServiceTest extends AbstractBugHandlerServiceTest {

    private BugModificationUseCase whenBugModificationUseCase;
    private DataStorageGateway<Bug> dataStorageGateway;

    @BeforeEach
    public void setUpExistingBug() {
        whenBugModificationUseCase = (BugModificationUseCase) createInstance();
        OutputDataForBugEntities.createExistingBug(whenBugModificationUseCase);
        setExistingBugId(Bug.IDGenerator.getCurrentID());
        whenBugModificationUseCase = null;
        whenBugModificationUseCase = (BugModificationUseCase) createInstance();
    }

    @AfterEach
    public void tearDown() {
        whenBugModificationUseCase = null;
        Bug.resetIDGenerator();
    }

    @Override
    public BugHandlerService createInstance() {
        presenterMock = mock(Presenter.class);
        if (dataStorageGateway == null)
            dataStorageGateway = new FakeDataStorageGateway();
        return new BugModificationUseCase(presenterMock, dataStorageGateway);
    }

    @Nested
    class CreateBugImplementationTest {
        @BeforeEach
        void setup() {
            Bug.resetIDGenerator();
        }

        @Test
        void createBug_GivenInputDataIsNullProjectId_ThenPresenterIsCalledOnceWithOutputDataWithNullProjectId() {
            InputData givenInputData = InputDataForBugEntities.createInputDataWithNullProjectId();
            whenBugModificationUseCase.createBug(givenInputData);
            verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
                assertNull(outputData.projectId);
                return true;
            }));
        }

        @Test
        void createBug_GivenInputDataIsValid_ThenPresenterIsCalledOnceWithImportantOutputData() {
            InputData givenInputData = InputDataForBugEntities.createValidInputData();
            whenBugModificationUseCase.createBug(givenInputData);
            verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
                assertEquals(givenInputData.name, outputData.name);
                assertEquals(givenInputData.description, outputData.description);
                assertEquals(false, outputData.inactivated);
                assertNull(outputData.message);
                return true;
            }));
        }

        @Test
        void createBug_GivenInputDataIsNullName_ThenPresenterIsCalledOnceWithOutputDataErrorMessage() {
            InputData givenInputData = InputDataForBugEntities.createInputDataWithNullName();
            whenBugModificationUseCase.createBug(givenInputData);
            verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
                assertEquals(ValidationException.NAME_IS_REQUIRED + System.lineSeparator(), outputData.message);
                return true;
            }));
        }

        @Test
        void createBug_GivenInputDataIsNullDescription_ThenPresenterIsCalledOnceWithOutputDataErrorMessage() {
            InputData givenInputData = InputDataForBugEntities.createInputDataWithNullDescription();
            whenBugModificationUseCase.createBug(givenInputData);
            verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
                assertEquals(ValidationException.DESCRIPTION_IS_REQUIRED + System.lineSeparator(), outputData.message);
                return true;
            }));
        }

        @Test
        void createBug_GivenInputDataIsNullProjectName_ThenPresenterIsCalledOnceWithOutputDataWithNullProjectName() {
            InputData givenInputData = InputDataForBugEntities.createInputDataWithNullProject();
            whenBugModificationUseCase.createBug(givenInputData);
            verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
                assertNull(outputData.projectName);
                return true;
            }));

        }

        @Test
        void createBug_GivenInputDataIsNullBothNameAndDescription_ThenPresenterIsCalledOnceWithOutputDataErrorMessage() {
            InputData givenInputData = InputDataForBugEntities.createInputDataWithNameAndDescriptionNull();
            whenBugModificationUseCase.createBug(givenInputData);
            verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
                assertNotNull(outputData.message);
                assertEquals(2, outputData.message.split("\n").length);
                return true;
            }));
        }

        @Test
        void createBug_GivenInputDataIsNullForNameAndDescriptionAndProjectName_ThenPresenterIsCalledOnceWithOutputDataErrorMessage() {
            InputData givenInputData = InputDataForBugEntities.createInputDataWithAllNullValues();
            whenBugModificationUseCase.createBug(givenInputData);
            verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
                assertNotNull(outputData.message);
                assertEquals(2, outputData.message.split(System.lineSeparator()).length);
                return true;
            }));
        }

        @Test
        void createBug_GivenTwoBugsAreCreated_ThenPresenterIsCalledForEachWithWithDifferentBugIdsWithinOneOfEachOther() {
            InputData givenInputData1 = InputDataForBugEntities.createValidInputData();
            InputData givenInputData2 = InputDataForBugEntities.createValidInputData();
            whenBugModificationUseCase.createBug(givenInputData1);
            verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
                assertEquals(1, outputData.id);
                return true;
            }));
            whenBugModificationUseCase = (BugModificationUseCase) createInstance();
            whenBugModificationUseCase.createBug(givenInputData2);
            verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
                assertEquals(2, outputData.id);
                return true;
            }));
        }

        @Test
        void createBug_GivenInputDataIsValid_ThenPresenterIsCalledWithOutputDataWithIncrementedId() {
            InputData givenInputData = InputDataForBugEntities.createValidInputData();
            whenBugModificationUseCase.createBug(givenInputData);
            verify(getHandlerServicePresenterMock()).present(argThat(outputData -> outputData.id == 1));
        }

        @Test
        void createBug_GivenInputDataFailsValidation_ThenPresenterIsCalledWithOutputDataIncrementIdShouldNotBeGenerated() {
            InputData givenInputData = InputDataForBugEntities.createInputDataWithNullName();
            whenBugModificationUseCase.createBug(givenInputData);
            verify(getHandlerServicePresenterMock()).present(argThat(outputData -> outputData.id == 0));
        }

        @Test
        void createBug_GivenInputDataIsValid_ThenPresenterIsCalledWithOutputDataShouldShowPersistedAsTrue() {
            InputData givenInputData = InputDataForBugEntities.createValidInputData();
            whenBugModificationUseCase.createBug(givenInputData);
            verify(getHandlerServicePresenterMock()).present(argThat(outputData -> outputData.persisted));
        }
    }

    @Nested
    class UpdateBugImplementationTest {
        @Test
        void updateBug_GivenInputDataIsValid_ThenPresenterIsCalledWithOutputDataWithUpdatedValues() {
            InputData givenInputData = InputDataForBugEntities.updateValidInputData(getExistingBugId());
            whenBugModificationUseCase.updateBug(givenInputData);
            verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
                assertEquals(givenInputData.name, outputData.name);
                assertEquals(givenInputData.description, outputData.description);
                assertEquals(givenInputData.projectId, outputData.projectId);
                assertEquals(givenInputData.projectName, outputData.projectName);
                return true;
            }));
        }

        @Test
        void updateBug_GivenInputDataIsAllNullButLoadedBugHasValidData_ThenPresenterIsCalledWithOutputDataAsUnchanged() {
            InputData givenInputData = InputDataForBugEntities.createInputDataWithAllNullValues();
            givenInputData.id = getExistingBugId();
            whenBugModificationUseCase.updateBug(givenInputData);
            verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
                assertEquals(givenInputData.id, outputData.id);
                return true;
            }));
        }

        @Test
        void updateBug_GivenInputDataIsValid_ThenPresenterIsCalledWithOutputDataWithPersistedAsTrue() {
            InputData givenInputData = InputDataForBugEntities.updateValidInputData(getExistingBugId());
            whenBugModificationUseCase.updateBug(givenInputData);
            verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
                assertEquals(true, outputData.persisted);
                return true;
            }));
        }

        @Test
        void updateBug_GivenInputNameIsEmpty_ThenPresenterIsCalledWithOutDataWithMessage() {
            InputData givenInputData = InputDataForBugEntities.updateInputDataWithEmptyName(getExistingBugId());
            whenBugModificationUseCase.updateBug(givenInputData);
            verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
                assertEquals(ValidationException.NAME_IS_REQUIRED + System.lineSeparator(), outputData.message);
                return true;
            }));
        }

        @Test
        void updateBug_GivenInputDescriptionIsEmpty_ThenPresenterIsCalledWithOutputDataWithMessage() {
            InputData givenInputData = InputDataForBugEntities.updateInputDataWithEmptyDescription(getExistingBugId());
            whenBugModificationUseCase.updateBug(givenInputData);
            verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
                assertEquals(ValidationException.DESCRIPTION_IS_REQUIRED + System.lineSeparator(), outputData.message);
                return true;
            }));
        }

        @Test
        void updateBug_GivenInputBothNameAndDescriptionAreEmpty_ThenPresenterIsCalledWithOutputDataWithMessage() {
            InputData givenInputData = InputDataForBugEntities.updateInputDataWithBothEmptyNameAndDescription(getExistingBugId());
            whenBugModificationUseCase.updateBug(givenInputData);
            verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
                assertNotNull(outputData.message);
                assertEquals(2, outputData.message.split(System.lineSeparator()).length);
                return true;
            }));
        }
    }

    @Nested
    class FindBugImplementationTest {

        private Integer existingBugId2;
        private Integer existingBugId3;

        public Integer getExistingBugId2() {
            return existingBugId2;
        }

        public void setExistingBugId2(Integer existingBugId2) {
            this.existingBugId2 = existingBugId2;
        }

        public Integer getExistingBugId3() {
            return existingBugId3;
        }

        public void setExistingBugId3(Integer existingBugId3) {
            this.existingBugId3 = existingBugId3;
        }

        @BeforeEach
        void setUp() {
            whenBugModificationUseCase = (BugModificationUseCase) createInstance();
            // create 2 more bugs and set the ids
            OutputDataForBugEntities.createExistingBug(whenBugModificationUseCase);
            setExistingBugId2(Bug.IDGenerator.getCurrentID());
            OutputDataForBugEntities.createExistingBug(whenBugModificationUseCase);
            setExistingBugId3(Bug.IDGenerator.getCurrentID());
            whenBugModificationUseCase = null;
            whenBugModificationUseCase = (BugModificationUseCase) createInstance();
        }

        @Test
        void findBug_GivenBugIdIsValid_ThenPresenterIsCalledWithOutputDataWithValidValues() {
            InputData givenInputData = InputDataForBugEntities.findValidInputData(getExistingBugId());
            Bug storedBug = dataStorageGateway.find(givenInputData.id);
            whenBugModificationUseCase.findBug(givenInputData);
            verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
                assertEquals(storedBug.getId(), outputData.id);
                assertEquals(storedBug.getName(), outputData.name);
                assertEquals(storedBug.getDescription(), outputData.description);
                assertEquals(storedBug.getProjectId(), outputData.projectId);
                assertEquals(storedBug.getProjectName(), outputData.projectName);
                return true;
            }));
        }

        @Test
        void findBug_GivenBugIdIsInvalid_ThenPresenterIsCalledWithOutputDataWithAMessage() {
            InputData givenInputData = InputDataForBugEntities.findValidInputData(getExistingBugId3() + 1);
            whenBugModificationUseCase.findBug(givenInputData);
            verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
                assertNotNull(outputData.message);
                return true;
            }));
        }

        @Test
        void findBug_GivenBugIdIsInvalid_ThenPresenterIsCalledWithOutputDataWithNullData() {
            InputData givenInputData = InputDataForBugEntities.findValidInputData(getExistingBugId3() + 1);
            whenBugModificationUseCase.findBug(givenInputData);
            verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
                assertEquals(0, outputData.id);
                assertNull(outputData.name);
                assertNull(outputData.description);
                assertNull(outputData.projectId);
                assertNull(outputData.projectName);
                return true;
            }));
        }

        @Test
        void findBug_GivenBugIdIsValid_ThenPresenterIsCalledWithOutputDataWithPersistedAsTrue() {
            InputData givenInputData = InputDataForBugEntities.findValidInputData(getExistingBugId2());
            whenBugModificationUseCase.findBug(givenInputData);
            verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
                assertEquals(true, outputData.persisted);
                return true;
            }));
        }

        @Test
        void findBug_GivenBugIdIsInvalid_ThenPresenterIsCalledWithOutputDataWithPersistedAsFalse() {
            InputData givenInputData = InputDataForBugEntities.findValidInputData(getExistingBugId3() + 1);
            whenBugModificationUseCase.findBug(givenInputData);
            verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
                assertEquals(false, outputData.persisted);
                return true;
            }));
        }

        @Test
        void findBug_GivenBugIdIsValid_ThenPresenterIsCalledWithOutputDataWithIdAsGivenId() {
            InputData givenInputData = InputDataForBugEntities.findValidInputData(getExistingBugId2());
            whenBugModificationUseCase.findBug(givenInputData);
            verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
                assertEquals(givenInputData.id, outputData.id);
                return true;
            }));
        }
    }

    @Nested
    class InactivateBugImplementationTest {
        @Test
        void inactivateBug_GivenBugIdIsValid_ThenPresenterIsCalledWithOutputDataHasInactiveTrue() {
            InputData givenInputData = InputDataForBugEntities.findValidInputData(getExistingBugId());
            whenBugModificationUseCase.inactivateBug(givenInputData);
            verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
                assertEquals(true, outputData.inactivated);
                return true;
            }));
        }

        @Test
        void inactivateBug_GivenBugIdIsInvalid_ThenPresenterIsCalledWithOutputDataWithAMessage() {
            InputData givenInputData = InputDataForBugEntities.findValidInputData(0);
            whenBugModificationUseCase.inactivateBug(givenInputData);
            verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
                assertNotNull(outputData.message);
                return true;
            }));
        }

        @Test
        void inactivateBug_GivenBugIdIsValid_ThenPresenterIsCalledWithOutputDataWithPersistedTrue() {
            InputData givenInputData = InputDataForBugEntities.findValidInputData(getExistingBugId());
            whenBugModificationUseCase.inactivateBug(givenInputData);
            verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
                assertEquals(true, outputData.persisted);
                return true;
            }));
        }

        @Test
        void inactivateBug_GivenBugIdIsInvalid_ThenPresenterIsCalledWithOutputDataWithPersistedFalse() {
            InputData givenInputData = InputDataForBugEntities.findValidInputData(0);
            whenBugModificationUseCase.inactivateBug(givenInputData);
            verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
                assertEquals(false, outputData.persisted);
                return true;
            }));
        }

        @Test
        void inactivateBug_GivenBugIdIsValid_ThenPresenterIsCalledWithOutputDataWithIdAsGivenId() {
            InputData givenInputData = InputDataForBugEntities.findValidInputData(getExistingBugId());
            whenBugModificationUseCase.inactivateBug(givenInputData);
            verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
                assertEquals(givenInputData.id, outputData.id);
                return true;
            }));
        }

        @Test
        void inactivateBug_GivenBugIdIsValid_ThenPresenterIsCalledWithOutputDataWithInactivatedAsTrue() {
            InputData givenInputData = InputDataForBugEntities.findValidInputData(getExistingBugId());
            whenBugModificationUseCase.inactivateBug(givenInputData);
            verify(getHandlerServicePresenterMock()).present(argThat(outputData -> {
                assertEquals(true, outputData.inactivated);
                return true;
            }));
        }
    }
}
