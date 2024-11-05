package com.github.devricks.bugzapperj.service.handler.data;

import com.github.devricks.bugzapperj.data.InputData;
import com.github.devricks.bugzapperj.data.OutputData;
import com.github.devricks.bugzapperj.entity.Bug;
import com.github.devricks.bugzapperj.service.DataHandlerService;

public class FakeDataHandlerService implements DataHandlerService {

    private static Bug.Builder getBuilder(InputData data, Bug foundBug) {
        Bug.Builder builder = new Bug.Builder();
        if (data.name == null) {
            builder.withName(foundBug.getName());
        } else {
            builder.withName(data.name);
        }
        if (data.description == null) {
            builder.withDescription(foundBug.getDescription());
        } else {
            builder.withDescription(data.description);
        }
        if (data.projectId == null) {
            builder.withProjectId(foundBug.getProjectId());
        } else {
            builder.withProjectId(data.projectId);
        }
        return builder;
    }

    @Override
    public Object convert(Object object, Class<?> clazz) {
        if (object instanceof Bug && clazz == OutputData.class) {
            OutputData outputData = (OutputData) manualConvertOfBug((Bug) object, clazz);
            outputData.status = "SUCCESS";
            return outputData;
        } else if (clazz == OutputData.class) {
            OutputData outputData = (OutputData) manualConvertOfObject(object, clazz);
            outputData.status = "SUCCESS";
            return outputData;
        } else {
            return manualConvertOfObject(object, clazz);
        }
    }

    @Override
    public Object convert(Object object, Exception e, Class<?> clazz) {
        OutputData outputData;
        if (object instanceof Bug && clazz == OutputData.class) {
            outputData = (OutputData) manualConvertOfBug((Bug) object, clazz);
            outputData.status = "FAILED";
            outputData.message = e.getMessage();
            return outputData;
        } else if (clazz == OutputData.class) {
            outputData = (OutputData) manualConvertOfObject(object, clazz);
            outputData.status = "FAILED";
            outputData.message = e.getMessage();
            return outputData;
        } else {
            return manualConvertOfObject(object, clazz);
        }
    }

    @Override
    public Object merge(Object objectDataStructure, Object object, Class<?> clazz) {
        if (objectDataStructure instanceof InputData data && object instanceof Bug foundBug && clazz == Bug.class) {
            Bug.Builder builder = getBuilder(data, foundBug);
            if (data.projectName == null) {
                builder.withProjectName(foundBug.getProjectName());
            } else {
                builder.withProjectName(data.projectName);
            }
            return builder.build();
        }
        return null;
    }

    private Object manualConvertOfBug(Bug object, Class<?> clazz) {
        if (clazz == OutputData.class) {
            OutputData outputData = new OutputData();
            outputData.id = object.getId();
            outputData.name = object.getName();
            outputData.description = object.getDescription();
            outputData.projectId = object.getProjectId();
            outputData.projectName = object.getProjectName();
            outputData.inactivated = object.isInactivated();
            outputData.persisted = object.isPersisted();
            return outputData;
        }
        return object;
    }

    private Object manualConvertOfObject(Object object, Class<?> clazz) {
        if (clazz == OutputData.class) {
            OutputData outputData = new OutputData();
            outputData.message = object.toString();
            try {
                outputData.id = (Integer) object.getClass().getDeclaredField("id").get(object);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                outputData.id = null;
            }
            try {
                outputData.name = object.getClass().getDeclaredField("name").toString();

            } catch (NoSuchFieldException e) {
                outputData.name = null;
            }
            try {
                outputData.description = object.getClass().getDeclaredField("description").toString();
            } catch (NoSuchFieldException e) {
                outputData.description = null;
            }
            try {
                outputData.projectId = (Integer) object.getClass().getDeclaredField("projectId").get(object);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                outputData.projectId = null;
            }
            try {
                outputData.projectName = object.getClass().getDeclaredField("projectName").toString();
            } catch (NoSuchFieldException e) {
                outputData.projectName = null;
            }
            try {
                outputData.inactivated = (Boolean) object.getClass().getDeclaredField("inactivated").get(object);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                outputData.inactivated = null;
            }
            try {
                outputData.persisted = (Boolean) object.getClass().getDeclaredField("persisted").get(object);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                outputData.persisted = null;
            }
            return outputData;
        }
        return object;
    }
}
