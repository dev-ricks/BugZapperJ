package com.github.devricks.bugzapperj.microservice.bug.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "bug", schema = "bugzapperj")
public class Bug {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private int type;

    @Column(name = "priority")
    private int priority;

    @Column(name = "description")
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

    public Bug(String name, int type, int priority, String description, int reporterId, int assigneeId, int bugStatus) {
        this.name = name;
        this.type = type;
        this.priority = priority;
        this.description = description;
        this.reporterId = reporterId;
        this.assigneeId = assigneeId;
        this.bugStatus = bugStatus;
    }

    public Bug() {
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
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", type=" + type +
                ", priority=" + priority +
                ", bugStatus=" + bugStatus +
                ", reporterId=" + reporterId +
                ", assigneeId=" + assigneeId +
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
