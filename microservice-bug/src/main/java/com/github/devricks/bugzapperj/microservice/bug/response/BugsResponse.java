package com.github.devricks.bugzapperj.microservice.bug.response;

import com.github.devricks.bugzapperj.microservice.bug.model.Bug;

import java.util.ArrayList;
import java.util.List;

public class BugsResponse extends BaseResponse {

    private List<Bug> bugs;

    public BugsResponse() {
        super("Bugs found");
        this.bugs = new ArrayList<>();
    }

    public BugsResponse(String message) {
        super(message);
        this.bugs = new ArrayList<>();
    }

    public BugsResponse(String message, int id) {
        super(message, id);
        this.bugs = new ArrayList<>();
    }

    public BugsResponse(List<Bug> bugs) {
        super("Bugs found");
        this.bugs = bugs;
    }

    public List<Bug> getBugs() {
        return bugs;
    }

    public void setBugs(List<Bug> bugs) {
        this.bugs = bugs;
    }
}
