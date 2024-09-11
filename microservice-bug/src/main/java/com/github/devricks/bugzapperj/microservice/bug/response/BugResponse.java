package com.github.devricks.bugzapperj.microservice.bug.response;

import com.github.devricks.bugzapperj.microservice.bug.model.Bug;

public class BugResponse extends BaseResponse {

    private int id;
    private String name;
    private int type;
    private int priority;
    private String description;
    private int bugStatus;
    private int reporterId;
    private int assigneeId;

    public BugResponse() {
        super("Success");
    }

    public BugResponse(int id, String name, int type, int priority, String description, int status, int reporterId, int assigneeId) {
        super("Success", id);
        this.name = name;
        this.type = type;
        this.priority = priority;
        this.description = description;
        this.bugStatus = status;
        this.reporterId = reporterId;
        this.assigneeId = assigneeId;
    }

    public BugResponse(Bug bug) {
        super("Success");
        this.id = bug.getId();
        this.name = bug.getName();
        this.type = bug.getType();
        this.priority = bug.getPriority();
        this.description = bug.getDescription();
        this.bugStatus = bug.getBugStatus();
        this.reporterId = bug.getReporterId();
        this.assigneeId = bug.getAssigneeId();
    }

    public BugResponse(String message) {
        super(message);
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

    public void setType(int type) {
        this.type = type;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBugStatus() {
        return bugStatus;
    }

    public void setBugStatus(int bugStatus) {
        this.bugStatus = bugStatus;
    }

    public int getReporterId() {
        return reporterId;
    }

    public void setReporterId(int reporterId) {
        this.reporterId = reporterId;
    }

    public int getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(int assigneeId) {
        this.assigneeId = assigneeId;
    }

}
