package com.github.devricks.bugzapperj.service;

import com.github.devricks.bugzapperj.presenter.Presenter;

public abstract class AbstractBugHandlerServiceTest implements BugHandlerServiceBoundaryTest {

    public Presenter presenterMock;
    public Integer existingBugId;

    @Override
    public Presenter getHandlerServicePresenterMock() {
        return this.presenterMock;
    }

    @Override
    public Integer getExistingBugId() {
        return this.existingBugId;
    }

    @Override
    public void setExistingBugId(Integer existingBugId) {
        this.existingBugId = existingBugId;
    }
}
