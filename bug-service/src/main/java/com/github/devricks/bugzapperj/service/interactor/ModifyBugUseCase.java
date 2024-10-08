package com.github.devricks.bugzapperj.service.interactor;

import com.github.devricks.bugzapperj.entity.Bug;
import com.github.devricks.bugzapperj.entity.Project;
import com.github.devricks.bugzapperj.entity.exception.ValidationException;

public class ModifyBugUseCase {

    public Bug createBug(String name, String description, Project project) throws ValidationException {
        Bug createdBug = new Bug.Builder()
                .withName(name)
                .withDescription(description)
                .withProject(project)
                .build();
        createdBug.validate();
        createdBug.createId();
        return createdBug;
    }
}
