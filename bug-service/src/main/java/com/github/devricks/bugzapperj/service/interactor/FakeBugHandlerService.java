package com.github.devricks.bugzapperj.service.interactor;

import com.github.devricks.bugzapperj.data.InputData;
import com.github.devricks.bugzapperj.data.OutputData;
import com.github.devricks.bugzapperj.entity.Bug;
import com.github.devricks.bugzapperj.entity.exception.ValidationException;
import com.github.devricks.bugzapperj.presenter.Presenter;
import com.github.devricks.bugzapperj.service.BugHandlerService;
import com.github.devricks.bugzapperj.service.DataHandlerService;
import com.github.devricks.bugzapperj.service.exception.BugNotFoundException;
import com.github.devricks.bugzapperj.service.handler.data.FakeDataHandlerService;
import com.github.devricks.bugzapperj.storage.DataStorageGateway;

public class FakeBugHandlerService implements BugHandlerService {

    private final DataHandlerService dataHandlerService = new FakeDataHandlerService();
    private final Presenter presenter;
    private final DataStorageGateway<Bug> dataStorageGateway;

    public FakeBugHandlerService(Presenter presenter, DataStorageGateway<Bug> dataStorageGateway) {
        this.presenter = presenter;
        this.dataStorageGateway = dataStorageGateway;
    }

    @Override
    public void createBug(InputData data) {
        Bug bug = new Bug.Builder().build(data);
        try {
            bug.validate();
        } catch (ValidationException e) {
            presenter.present((OutputData) dataHandlerService.convert(bug, e, OutputData.class));
            return;
        }
        bug.createId();
        Boolean persisted = dataStorageGateway.save(bug);
        bug.setPersisted(persisted);
        presenter.present((OutputData) dataHandlerService.convert(bug, OutputData.class));
    }

    @Override
    public void updateBug(InputData data) {
        Bug bug = dataStorageGateway.find(data.id);
        Bug mergedBug = (Bug) dataHandlerService.merge(data, bug, Bug.class);
        try {
            mergedBug.validate();
        } catch (ValidationException e) {
            presenter.present((OutputData) dataHandlerService.convert(bug, e, OutputData.class));
            return;
        }
        Boolean persisted = dataStorageGateway.save(mergedBug);
        mergedBug.setPersisted(persisted);
        presenter.present((OutputData) dataHandlerService.convert(mergedBug, OutputData.class));
    }

    @Override
    public void findBug(InputData data) {
        Bug bug = dataStorageGateway.find(data.id);
        if (bug == null) {
            presenter.present((OutputData) dataHandlerService.convert(new Bug(), new BugNotFoundException("Bug not found."), OutputData.class));
            return;
        }
        try {
            bug.validate();
        } catch (ValidationException e) {
            presenter.present((OutputData) dataHandlerService.convert(bug, e, OutputData.class));
            return;
        }
        presenter.present((OutputData) dataHandlerService.convert(bug, OutputData.class));
    }

    @Override
    public void inactivateBug(InputData data) {
        Bug bug = dataStorageGateway.find(data.id);
        if (bug == null) {
            presenter.present((OutputData) dataHandlerService.convert(new Bug(), new BugNotFoundException("Bug not found."), OutputData.class));
            return;
        }
        bug.setInactivated(true);
        Boolean persisted = dataStorageGateway.save(bug);
        bug.setPersisted(persisted);
        presenter.present((OutputData) dataHandlerService.convert(bug, OutputData.class));
    }
}
