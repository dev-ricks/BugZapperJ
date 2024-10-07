package com.github.devricks.bugzapperj.entity;

import com.github.devricks.bugzapperj.entity.exception.ValidationException;
import com.github.devricks.bugzapperj.entity.util.IDGenerator;
import com.github.devricks.bugzapperj.entity.validation.Validator;

import java.util.HashMap;
import java.util.Map;

public class Bug implements Validator {
    private int id;
    private String name;
    private String description;
    private Project project;

    public static IDGenerator IDGeneratorBug = new IDGenerator();

    private Bug() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Project getProject() {
        return project;
    }

    public void validate() throws ValidationException {
        Map<String, String> errorMessagesMap = new HashMap<>();
        if (name == null || name.isBlank()) {
            errorMessagesMap.put("name", "Name cannot be empty");
        }
        if (description == null || description.isBlank()) {
            errorMessagesMap.put("description", "Description cannot be empty");
        }
        if (project == null) {
            errorMessagesMap.put("project", "Project cannot be null");
        }
        if (!errorMessagesMap.isEmpty()) {
            throw new ValidationException(errorMessagesMap);
        }
    }

    public void createId() {
        this.id = IDGeneratorBug.generateID();
    }

    static public void resetIDGenerator() {
        IDGeneratorBug.reset();
    }

    public static class Builder {
        private String name;
        private String description;
        private Project project;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setProject(Project project) {
            this.project = project;
            return this;
        }

        public Bug build() {
            Bug bug = new Bug();
            bug.name = this.name;
            bug.description = this.description;
            bug.project = this.project;
            return bug;
        }
    }
}
