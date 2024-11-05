package com.github.devricks.bugzapperj.entity;

import com.github.devricks.bugzapperj.data.InputData;
import com.github.devricks.bugzapperj.entity.exception.ValidationException;
import com.github.devricks.bugzapperj.util.HelperFunctions;

import java.util.HashMap;
import java.util.Map;

public class Bug extends EntityBase {
    private String name;
    private String description;
    private Integer projectId;
    private String projectName;
    private Boolean inactivated;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public Boolean isInactivated() {
        return inactivated;
    }

    public void setInactivated(Boolean inactivated) {
        this.inactivated = inactivated;
    }

    @Override
    public void validate() {
        Map<String, String> errorMessagesMap = new HashMap<>();
        if (name == null || HelperFunctions.isBlank(name)) {
            errorMessagesMap.put("name", ValidationException.NAME_IS_REQUIRED);
        }
        if (description == null || HelperFunctions.isBlank(description)) {
            errorMessagesMap.put("description", ValidationException.DESCRIPTION_IS_REQUIRED);
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
        private Integer projectId;
        private String projectName;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withProjectId(Integer projectId) {
            this.projectId = projectId;
            return this;
        }

        public Builder withProjectName(String projectName) {
            this.projectName = projectName;
            return this;
        }

        @Override
        public Bug build() {
            Bug bug = new Bug();
            bug.name = this.name;
            bug.description = this.description;
            bug.projectId = this.projectId;
            bug.projectName = this.projectName;
            bug.setInactivated(false);
            return bug;
        }

        @Override
        public Bug build(InputData data) {
            Bug bug = new Bug();
            bug.name = data.name;
            bug.description = data.description;
            bug.projectId = data.projectId;
            bug.projectName = data.projectName;
            bug.setInactivated(false);
            return bug;
        }
    }
}
