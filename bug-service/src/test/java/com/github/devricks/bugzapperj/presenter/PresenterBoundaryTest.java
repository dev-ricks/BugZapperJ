package com.github.devricks.bugzapperj.presenter;

import com.github.devricks.bugzapperj.data.OutputData;
import com.github.devricks.bugzapperj.data.ViewModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public interface PresenterBoundaryTest {

    Presenter createInstance();

    @Test
    default void present_GivenOutputData_ThenReturnsViewModelWithProperId() {
        OutputData givenOutputData = new OutputData();
        givenOutputData.id = 1;
        Presenter whenPresenter = createInstance();
        ViewModel thenView = whenPresenter.present(givenOutputData);
        assertEquals(givenOutputData.id, thenView.id);
    }

    @Test
    default void present_GivenOutputData_ThenReturnsViewModelWithProperStatus() {
        OutputData givenOutputData = new OutputData();
        givenOutputData.status = "SUCCESS";
        Presenter whenPresenter = createInstance();
        ViewModel thenView = whenPresenter.present(givenOutputData);
        assertEquals(givenOutputData.status, thenView.status);
    }

    @Test
    default void present_GivenOutputData_ThenReturnsViewModelWithProperMessage() {
        OutputData givenOutputData = new OutputData();
        givenOutputData.message = "Bug was created successfully";
        Presenter whenPresenter = createInstance();
        ViewModel thenView = whenPresenter.present(givenOutputData);
        assertEquals(givenOutputData.message, thenView.message);
    }
}

class FakePresenterInterfaceTest implements PresenterBoundaryTest {
    @Override
    public Presenter createInstance() {
        return new FakePresenter();
    }
}
