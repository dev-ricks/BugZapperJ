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

public class BugModificationUseCase implements BugHandlerService {

    private final DataHandlerService dataHandlerService = new FakeDataHandlerService();
    private final Presenter presenter;
    private final DataStorageGateway<Bug> dataStorageGateway;

    public BugModificationUseCase(Presenter presenter, DataStorageGateway<Bug> dataStorageGateway) {
        this.presenter = presenter;
        this.dataStorageGateway = dataStorageGateway;
    }

    public void createBug(InputData data) {
        Bug createdBug = new Bug.Builder().withName(data.name).withDescription(data.description).withProjectId(data.projectId).withProjectName(data.projectName).build();
        try {
            createdBug.validate();
        } catch (ValidationException e) {
            presenter.present((OutputData) dataHandlerService.convert(createdBug, e, OutputData.class));
            return;
        }
        createdBug.createId();
        boolean persisted = dataStorageGateway.save(createdBug);
        createdBug.setPersisted(persisted);
        presenter.present((OutputData) dataHandlerService.convert(createdBug, OutputData.class));
    }

    public void updateBug(InputData data) {
        Bug foundBug = dataStorageGateway.find(data.id);
        Bug updatedBug = (Bug) dataHandlerService.merge(data, foundBug, Bug.class);
        updatedBug.setId(data.id);
        try {
            updatedBug.validate();
        } catch (ValidationException e) {
            presenter.present((OutputData) dataHandlerService.convert(updatedBug, e, OutputData.class));
            return;
        }
        boolean persisted = dataStorageGateway.save(updatedBug);
        updatedBug.setPersisted(persisted);
        presenter.present((OutputData) dataHandlerService.convert(updatedBug, OutputData.class));
    }

    public void findBug(InputData data) {
        Bug foundBug = dataStorageGateway.find(data.id);
        if (foundBug == null) {
            presenter.present((OutputData) dataHandlerService.convert(new Bug(), new BugNotFoundException("Bug not found."), OutputData.class));
            return;
        }
        presenter.present((OutputData) dataHandlerService.convert(foundBug, OutputData.class));
    }

    public void inactivateBug(InputData data) {
        Bug foundBug = dataStorageGateway.find(data.id);
        if (foundBug == null) {
            presenter.present((OutputData) dataHandlerService.convert(new Bug(), new BugNotFoundException("Bug not found."), OutputData.class));
            return;
        }
        foundBug.setInactivated(true);
        boolean persisted = dataStorageGateway.save(foundBug);
        foundBug.setPersisted(persisted);
        presenter.present((OutputData) dataHandlerService.convert(foundBug, OutputData.class));
    }
}
