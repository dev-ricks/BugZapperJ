package com.github.devricks.bugzapperj.microservice.bug.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "bug", schema = "bugzapperj")
public class Bug {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bug_id_seq")
    @SequenceGenerator(name = "bug_id_seq", sequenceName = "bugzapperj.seq_bug_id_gen", allocationSize = 1)
    @Column(name = "id")
    private int id;

    @Column(name = "name", columnDefinition = "TEXT", length = 75)
    private String name;

    @Column(name = "type")
    private int type;

    @Column(name = "priority")
    private int priority;

    @Column(name = "description", columnDefinition = "TEXT", length = 250)
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

    public int getType() {
        return type;
    }

    public int getPriority() {
        return priority;
    }

    public String getDescription() {
        return description;
    }

    public int getAssigneeId() {
        return assigneeId;
    }

    public int getReporterId() {
        return reporterId;
    }

    public int getBugStatus() {
        return bugStatus;
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
