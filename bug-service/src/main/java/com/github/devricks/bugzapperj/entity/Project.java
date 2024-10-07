package com.github.devricks.bugzapperj.entity;

import com.github.devricks.bugzapperj.entity.exception.ValidationException;
import com.github.devricks.bugzapperj.entity.util.IDGenerator;
import com.github.devricks.bugzapperj.entity.validation.Validator;

import java.util.*;

public class Project implements Validator {
    private int id;
    private String name;
    private Set<Bug> bugs;

    static public IDGenerator IDGeneratorProject = new IDGenerator();

    private Project() {
    }

    public int getId() {
        return id;
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

    public void validate() throws ValidationException {
        Map<String, String> errorMessagesMap = new HashMap<>();
        if (name == null || name.isBlank()) {
            errorMessagesMap.put("name", "Name cannot be empty");
        }
        if (!errorMessagesMap.isEmpty()) {
            throw new ValidationException(errorMessagesMap);
        }
    }

    static public void resetIDGenerator() {
        IDGeneratorProject.reset();
    }

    public static class Builder {
        private String name;
        private Set<Bug> bugs = new HashSet<>();

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setBugs(Set<Bug> bugs) {
            this.bugs = bugs;
            return this;
        }

        public Project build() {
            Project project = new Project();
            project.id = IDGeneratorProject.generateID();
            project.name = name;
            project.bugs = Objects.requireNonNullElseGet(bugs, HashSet::new);
            return project;
        }
    }
}
