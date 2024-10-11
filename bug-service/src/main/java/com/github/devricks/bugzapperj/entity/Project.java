package com.github.devricks.bugzapperj.entity;

import com.github.devricks.bugzapperj.entity.exception.ValidationException;
import com.github.devricks.bugzapperj.util.HelperFunctions;

import java.util.*;

public class Project extends EntityBase {
    private String name;
    private Set<Bug> bugs;

    private Project() {
    }

    public String getName() {
        return name;
    }

    public Set<Bug> getBugs() {
        return bugs;
    }

    public void addBugs(Set<Bug> bugs) {
        if (bugs != null) {
            this.bugs.addAll(bugs);
        }
    }

    public void addBug(Bug bug) {
        this.bugs.add(bug);
    }

    @Override
    public void validate() {
        Map<String, String> errorMessagesMap = new HashMap<>();
        if (name == null || HelperFunctions.isBlank(name)) {
            errorMessagesMap.put("name", "Name cannot be empty");
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
        private Set<Bug> bugs = new HashSet<>();

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withBugs(Set<Bug> bugs) {
            this.bugs = bugs;
            return this;
        }

        @Override
        public Project build() {
            Project project = new Project();
            project.name = name;
            project.bugs = Objects.requireNonNullElseGet(bugs, HashSet::new);
            return project;
        }
    }
}
