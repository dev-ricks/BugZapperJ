package com.github.devricks.bugzapperj.microservice.bug.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "bug", schema = "bugzapperj")
public class Bug implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bug_id_seq")
    @SequenceGenerator(name = "bug_id_seq", sequenceName = "bugzapperj.seq_bug_id_gen", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private int id;

    @Column(name = "name", columnDefinition = "TEXT", length = 75, nullable = false)
    @NotBlank(message = "Name is required")
    private String name;

    @Column(name = "type")
    private int type;

    @Column(name = "priority")
    private int priority;

    @Column(name = "description", columnDefinition = "TEXT", length = 250, nullable = false)
    @NotBlank(message = "Description is required")
    private String description;

    @Column(name = "status")
    private int bugStatus;

    @Column(name = "reporter_id")
    private int reporterId;

    @Column(name = "assignee_id")
    private int assigneeId;

    public Bug(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Bug() {
        // Default constructor
        this.name = "";
        this.description = "";
        this.type = 0;
        this.priority = 0;
        this.bugStatus = 0;
        this.reporterId = 0;
        this.assigneeId = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int i) {
        this.priority = i;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(int i) {
        this.assigneeId = i;
    }

    public int getReporterId() {
        return reporterId;
    }

    public void setReporterId(int i) {
        this.reporterId = i;
    }

    public int getBugStatus() {
        return bugStatus;
    }

    public void setBugStatus(int i) {
        this.bugStatus = i;
    }

    @Override
    public String toString() {
        return "Bug{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", type=" + getType() +
                ", priority=" + getPriority() +
                ", bugStatus=" + getBugStatus() +
                ", reporterId=" + getReporterId() +
                ", assigneeId=" + getAssigneeId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bug bug = (Bug) o;
        return Objects.equals(name, bug.name) &&
                Objects.equals(description, bug.description) &&
                id == bug.id &&
                type == bug.type &&
                priority == bug.priority &&
                bugStatus == bug.bugStatus &&
                reporterId == bug.reporterId &&
                assigneeId == bug.assigneeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, type, priority, bugStatus, reporterId, assigneeId);
    }
}
