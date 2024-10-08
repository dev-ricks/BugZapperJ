package com.github.devricks.bugzapperj.entity;

import com.github.devricks.bugzapperj.entity.exception.ValidationException;

import java.util.HashMap;
import java.util.Map;

public class Bug extends EntityBase {
    private String name;
    private String description;
    private Project project;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Project getProject() {
        return project;
    }

    @Override
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

    @Override
    public void createId() {
        setId(IDGenerator.generateID());
    }

    public static class Builder implements EntityBuilder {
        private String name;
        private String description;
        private Project project;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withProject(Project project) {
            this.project = project;
            return this;
        }

        @Override
        public Bug build() {
            Bug bug = new Bug();
            bug.name = this.name;
            bug.description = this.description;
            bug.project = this.project;
            return bug;
        }
    }
}
