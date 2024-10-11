package com.github.devricks.bugzapperj.service.converter;

import com.github.devricks.bugzapperj.data.OutputData;
import com.github.devricks.bugzapperj.entity.Bug;

public class FakeObjectConversionService implements ObjectConversionService {

    @Override
    public Object convert(Object object, Class<?> clazz) {
        if (object instanceof Bug && clazz == OutputData.class) {
            OutputData outputData = (OutputData) manualConvertOfBug(object);
            outputData.status = "SUCCESS";
            return outputData;
        }
        return new OutputData();
    }

    @Override
    public Object convert(Object object, Exception e, Class<?> clazz) {
        if (object instanceof Bug) {
            OutputData outputData = (OutputData) manualConvertOfBug(object);
            outputData.status = "FAILED";
            outputData.message = e.getMessage();
            return outputData;
        }
        return new OutputData();
    }

    private Object manualConvertOfBug(Object object) {
        Bug bug = (Bug) object;
        OutputData outputData = new OutputData();
        outputData.id = bug.getId();
        outputData.name = bug.getName();
        outputData.description = bug.getDescription();
        outputData.project = bug.getProject();
        return outputData;
    }
}
