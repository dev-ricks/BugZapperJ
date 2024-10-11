package com.github.devricks.bugzapperj.service.interactor;

import com.github.devricks.bugzapperj.data.InputData;
import com.github.devricks.bugzapperj.data.OutputData;
import com.github.devricks.bugzapperj.entity.Bug;
import com.github.devricks.bugzapperj.entity.exception.ValidationException;
import com.github.devricks.bugzapperj.service.BugHandlerService;
import com.github.devricks.bugzapperj.service.converter.FakeObjectConversionService;
import com.github.devricks.bugzapperj.service.converter.ObjectConversionService;

public class BugModificationUseCase implements BugHandlerService {

    private final ObjectConversionService objectConversionService = new FakeObjectConversionService();

    public OutputData createBug(InputData data) {
        Bug createdBug = new Bug.Builder().withName(data.name).withDescription(data.description).withProject(data.project).build();
        try {
            createdBug.validate();
        } catch (ValidationException e) {
            return (OutputData) objectConversionService.convert(createdBug, e, OutputData.class);
        }
        createdBug.createId();
        return (OutputData) objectConversionService.convert(createdBug, OutputData.class);
    }

    public OutputData updateBug(InputData data) {
        return new OutputData();
    }
}
